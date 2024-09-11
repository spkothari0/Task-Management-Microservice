package com.shreyas.service.implementation;

import com.shreyas.Utility.GenericBeanMapper;
import com.shreyas.bean.TaskAssignmentEvent;
import com.shreyas.bean.TaskBean;
import com.shreyas.entity.Task;
import com.shreyas.entity.TaskStatus;
import com.shreyas.repository.TaskRepo;
import com.shreyas.service.interfaces.KafkaService;
import com.shreyas.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
        List<Task> tasks = taskRepo.findAll();
        tasks = tasks.stream().filter(x -> status == null || x.getStatus().equalsIgnoreCase(status.name())).toList();
        return GenericBeanMapper.mapList(tasks, TaskBean.class, mapper);
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
        List<Task> taskList = taskRepo.findByAssignedUserId(userId);
        taskList = taskList.stream().filter(x -> status == null || x.getStatus().equalsIgnoreCase(status.name())).toList();
        return GenericBeanMapper.mapList(taskList, TaskBean.class, mapper);
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
        return GenericBeanMapper.map(t, TaskBean.class, mapper);
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
