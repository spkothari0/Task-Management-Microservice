package com.shreyas.service.interfaces;

import com.shreyas.AppConstant;
import com.shreyas.controller.APIResponse;
import com.shreyas.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.UUID;

@Service
@FeignClient(name = "TASK-SERVICE")
public interface TaskService {
    @GetMapping(value = "/api/v1/tasks/{id}")
    APIResponse<TaskDto> getTaskById(@PathVariable UUID id, @RequestHeader(AppConstant.CORRELATION_ID) String correlationId,
                                     @RequestHeader(AppConstant.Authorization_Header) String authorizationHeader) throws Exception;
}
