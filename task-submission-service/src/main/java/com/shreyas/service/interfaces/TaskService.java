package com.shreyas.service.interfaces;

import com.shreyas.bean.TaskDto;
import com.shreyas.config.FeignClientConfig;
import com.shreyas.controller.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "TASK-SERVICE", configuration = FeignClientConfig.class)
public interface TaskService {
    @GetMapping(value = "/api/v1/tasks/{id}")
    APIResponse<TaskDto> getTaskById(@PathVariable UUID id) throws Exception;

    @PutMapping(value = "/api/v1/tasks/{id}/complete")
    APIResponse<TaskDto> markTaskAsComplete(@PathVariable UUID id) throws Exception;
}
