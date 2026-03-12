package com.shreyas.service.implementation;

import com.shreyas.Utility.GenericBeanMapper;
import com.shreyas.bean.TaskAssignmentEvent;
import com.shreyas.bean.TaskBean;
import com.shreyas.entity.Task;
import com.shreyas.entity.TaskStatus;
import com.shreyas.repository.TaskRepo;
import com.shreyas.search.document.TaskDocument;
import com.shreyas.search.repository.TaskSearchRepository;
import com.shreyas.service.interfaces.KafkaService;
import com.shreyas.service.interfaces.TaskService;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final ModelMapper mapper;
    private final KafkaService kafkaService;
    private final TaskSearchRepository taskSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * @param task
     * @param author
     * @param requesterRole
     * @return TaskBean
     * @throws Exception
     */
    @Override
    public TaskBean createTask(TaskBean task, UUID author, String requesterRole) throws Exception {
        if (!requesterRole.equalsIgnoreCase("ROLE_ADMIN"))
            throw new Exception("Only admin can create tasks");


        Task newTask = GenericBeanMapper.map(task, Task.class, mapper);
        newTask.setStatus(TaskStatus.PENDING.name());
        newTask.setCreatedAt(LocalDateTime.now());
        newTask.setCreatedBy(author);
        newTask.setModifiedBy(author);
        newTask.setAssignedUserId(author);
        newTask = taskRepo.save(newTask);
        indexTask(newTask);
        return GenericBeanMapper.map(newTask, TaskBean.class, mapper);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public TaskBean getTaskById(UUID id) throws Exception {
        Task t = getTask_ById(id);
        return GenericBeanMapper.map(t, TaskBean.class, mapper);
    }

    /**
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public List<TaskBean> getAllTasks(TaskStatus status) throws Exception {
        return searchTasks(null, status, null);
    }

    @Override
    public List<TaskBean> searchTasks(String query, TaskStatus status, UUID assignedUserId) throws Exception {
        try {
            List<TaskDocument> documents = searchTaskDocuments(query, status, assignedUserId);
            if (documents.isEmpty()) {
                return Collections.emptyList();
            }
            List<Task> tasks = documents.stream()
                    .map(doc -> taskRepo.findById(doc.getUuid()).orElse(null))
                    .filter(task -> task != null)
                    .toList();
            return GenericBeanMapper.mapList(tasks, TaskBean.class, mapper);
        } catch (Exception ex) {
            log.warn("Elasticsearch unavailable. Falling back to database search. Cause: {}", ex.getMessage());
            List<Task> tasks = taskRepo.findAll();
            tasks = tasks.stream()
                    .filter(task -> status == null || task.getStatus().equalsIgnoreCase(status.name()))
                    .filter(task -> assignedUserId == null || task.getAssignedUserId().equals(assignedUserId))
                    .filter(task -> query == null || query.isBlank() || containsSearchText(task, query))
                    .toList();
            return GenericBeanMapper.mapList(tasks, TaskBean.class, mapper);
        }
    }

    /**
     * @param id
     * @param task
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public TaskBean updateTask(UUID id, TaskBean task, UUID userId) throws Exception {

        Task t = getTask_ById(id);
        t.setModifiedBy(userId);

        if (!task.getTags().isEmpty())
            t.setTags(task.getTags());
        if (!task.getDescription().isEmpty())
            t.setDescription(task.getDescription());
        if (task.getTitle() != null)
            t.setTitle(task.getTitle());
        if (task.getImageURL() != null)
            t.setImageURL(task.getImageURL());
        if (task.getDeadLine() != null)
            t.setDeadLine(task.getDeadLine());
        if(task.getAssignedUserId()!=null)
            t.setAssignedUserId(task.getAssignedUserId());
        if (task.getStatus()!= null)
            t.setStatus(task.getStatus().name());

        t = taskRepo.save(t);
        indexTask(t);
        return GenericBeanMapper.map(t, TaskBean.class, mapper);
    }

    /**
     * @param taskId
     * @throws Exception
     */
    @Override
    public void deleteTask(UUID taskId) throws Exception {
        getTask_ById(taskId);
        taskRepo.deleteById(taskId);
        deleteIndexedTask(taskId);
    }

    /**
     * @param userId
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public TaskBean assignTaskToUser(UUID userId, UUID taskId, String token) throws Exception {
        Task t = getTask_ById(taskId);
        t.setAssignedUserId(userId);
        t.setStatus(TaskStatus.IN_PROGRESS.name());
        t = taskRepo.save(t);
        indexTask(t);

        TaskAssignmentEvent event = new TaskAssignmentEvent(taskId, userId);
        // send task assignment to task
        sendTaskAssignedEvent(event,token);

        return GenericBeanMapper.map(t, TaskBean.class, mapper);
    }

    /**
     * @param userId
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public List<TaskBean> assignedUserTasks(UUID userId, TaskStatus status) throws Exception {
        return searchTasks(null, status, userId);
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    public TaskBean completeTask(UUID taskId) throws Exception {
        Task t = getTask_ById(taskId);
        t.setStatus(TaskStatus.COMPLETED.name());
        t.setModifiedBy(t.getAssignedUserId());
        t = taskRepo.save(t);
        indexTask(t);
        return GenericBeanMapper.map(t, TaskBean.class, mapper);
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    public TaskBean cancelTask(UUID taskId) throws Exception {
        Task t = getTask_ById(taskId);
        t.setStatus(TaskStatus.CANCELLED.name());
        t.setModifiedBy(t.getAssignedUserId());
        t = taskRepo.save(t);
        indexTask(t);
        return GenericBeanMapper.map(t, TaskBean.class, mapper);
    }

    private List<TaskDocument> searchTaskDocuments(String query, TaskStatus status, UUID assignedUserId) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if (query != null && !query.isBlank()) {
            boolQuery.must(m -> m.multiMatch(mm -> mm
                    .query(query)
                    .fields("title", "description", "tags")));
        }

        if (status != null) {
            boolQuery.filter(f -> f.term(t -> t.field("status").value(status.name())));
        }

        if (assignedUserId != null) {
            boolQuery.filter(f -> f.term(t -> t.field("assignedUserId").value(assignedUserId.toString())));
        }

        Query finalQuery = Query.of(q -> q.bool(boolQuery.build()));
        NativeQuery searchQuery = NativeQuery.builder().withQuery(finalQuery).build();
        SearchHits<TaskDocument> hits = elasticsearchOperations.search(searchQuery, TaskDocument.class);

        return hits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    private boolean containsSearchText(Task task, String query) {
        String normalized = query.toLowerCase();
        boolean inTitle = task.getTitle() != null && task.getTitle().toLowerCase().contains(normalized);
        boolean inDescription = task.getDescription() != null && task.getDescription().toLowerCase().contains(normalized);
        boolean inTags = task.getTags() != null && task.getTags().stream().anyMatch(tag -> tag != null && tag.toLowerCase().contains(normalized));
        return inTitle || inDescription || inTags;
    }

    private void indexTask(Task task) {
        try {
            taskSearchRepository.save(TaskDocument.fromTask(task));
        } catch (Exception ex) {
            log.warn("Failed to index task {} in Elasticsearch: {}", task.getId(), ex.getMessage());
        }
    }

    private void deleteIndexedTask(UUID taskId) {
        try {
            taskSearchRepository.deleteById(taskId.toString());
        } catch (Exception ex) {
            log.warn("Failed to remove task {} from Elasticsearch index: {}", taskId, ex.getMessage());
        }
    }

    private Task getTask_ById(UUID taskId) throws Exception {
        Optional<Task> t = taskRepo.findById(taskId);
        if (t.isPresent())
            return t.get();
        else
            throw new RuntimeException("Task not found with id: " + taskId);
    }

    public void sendTaskAssignedEvent(TaskAssignmentEvent event, String token) {
        try {
            kafkaService.sendMessage(event,token);
        }catch (Exception e) {
            log.error("Error in kafka while sending task assignment event: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
