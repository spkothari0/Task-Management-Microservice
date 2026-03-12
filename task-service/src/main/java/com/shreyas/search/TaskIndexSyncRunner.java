package com.shreyas.search;

import com.shreyas.entity.Task;
import com.shreyas.repository.TaskRepo;
import com.shreyas.search.document.TaskDocument;
import com.shreyas.search.repository.TaskSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskIndexSyncRunner {

    private final TaskRepo taskRepo;
    private final TaskSearchRepository taskSearchRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void syncTaskIndexOnStartup() {
        try {
            List<Task> tasks = taskRepo.findAll();
            if (tasks.isEmpty()) {
                return;
            }
            List<TaskDocument> documents = tasks.stream()
                    .map(TaskDocument::fromTask)
                    .toList();
            taskSearchRepository.saveAll(documents);
            log.info("Synchronized {} tasks to Elasticsearch index", documents.size());
        } catch (Exception ex) {
            log.warn("Failed to sync tasks to Elasticsearch index at startup: {}", ex.getMessage());
        }
    }
}
