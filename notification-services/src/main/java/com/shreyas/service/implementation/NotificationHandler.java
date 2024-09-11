package com.shreyas.service.implementation;

import com.shreyas.AppConstant;
import com.shreyas.controller.APIResponse;
import com.shreyas.dto.TaskAssignmentEvent;
import com.shreyas.dto.TaskDto;
import com.shreyas.dto.UserDto;
import com.shreyas.service.interfaces.EmailService;
import com.shreyas.service.interfaces.TaskService;
import com.shreyas.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationHandler {
    private final UserService userService;
    private final TaskService taskService;
    private final AppConstant appConstant;
    private final EmailService emailService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void receiveTaskAssignmentMessage(TaskAssignmentEvent event, @Header(AppConstant.CORRELATION_ID) String correlationId, @Header(AppConstant.Authorization_Header) String token){
        log.info("Received task assignment message {} with correlation id as: {} and token is {}",event, correlationId, token);
        try{

            APIResponse<UserDto> userResponse = userService.getUser(event.getUserId(),correlationId,token);
            APIResponse<TaskDto> taskResponse = taskService.getTaskById(event.getTaskId(),correlationId,token);
            if(appConstant.IsMailServiceEnabled()){
                String message = "Dear "+userResponse.getData().getFirstName()+", please check the task assigned to you: ("+taskResponse.getData().getId()+") - "+taskResponse.getData().getTitle();
                emailService.sendEmail(userResponse.getData().getEmail(), "Assigned task",message);
                log.info("Send message to the user: {}", message);
            }else{
                log.info("Mail service is disabled, not sending email.");
                log.info("Mail should be sent to user {} with email id as {}",userResponse.getData().getFirstName(),userResponse.getData().getEmail());
                log.info("The task assigned to user is {} - {}",taskResponse.getData().getId(),taskResponse.getData().getTitle());
            }

        }catch (Exception e){
            log.error("Error in processing task assignment message: {}", e.getMessage());
            e.printStackTrace();
            return; // Handle the exception appropriately, e.g., retry, send error notification, etc.
        }
    }
}
