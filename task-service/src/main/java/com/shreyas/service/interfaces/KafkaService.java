package com.shreyas.service.interfaces;

import com.shreyas.bean.TaskAssignmentEvent;

public interface KafkaService {
    public void sendMessage(TaskAssignmentEvent event, String token) throws Exception;
}
