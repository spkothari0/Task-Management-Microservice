package com.shreyas.service.implementation;

import com.shreyas.AppConstant;
import com.shreyas.controller.APIResponse;
import com.shreyas.dto.TaskAssignmentEvent;
import com.shreyas.dto.TaskDto;
import com.shreyas.dto.UserDto;
import com.shreyas.service.interfaces.TaskService;
import com.shreyas.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final UserService userService;
    private final TaskService taskService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void receiveTaskAssignmentMessage(TaskAssignmentEvent event, @Header(AppConstant.CORRELATION_ID) String correlationId, @Header(AppConstant.Authorization_Header) String token){
        log.info("Received task assignment message {} with correlation id as: {} and token is {}",event, correlationId, token);
        try{

            APIResponse<UserDto> userResponse = userService.getUser(event.getUserId(),correlationId,token);
            log.info("Send mail to user {} with email id as {}",userResponse.getData().getFirstName(),userResponse.getData().getEmail());
            APIResponse<TaskDto> taskResponse = taskService.getTaskById(event.getTaskId(),correlationId,token);
            log.info("The task assigned to user is {} - {}",taskResponse.getData().getId(),taskResponse.getData().getTitle());

        }catch (Exception e){
            log.error("Error in processing task assignment message: {}", e.getMessage());
            e.printStackTrace();
            return; // Handle the exception appropriately, e.g., retry, send error notification, etc.
        }
    }
}
