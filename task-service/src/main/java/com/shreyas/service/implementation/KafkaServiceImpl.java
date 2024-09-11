package com.shreyas.service.implementation;

import com.shreyas.AppConstant;
import com.shreyas.bean.TaskAssignmentEvent;
import com.shreyas.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final NewTopic kafkaTopic;
    private final KafkaTemplate<String, TaskAssignmentEvent> kafkaTemplate;
    private final NewTopic topic;

    public void sendMessage(TaskAssignmentEvent event, String token) throws Exception {
        log.info("Sending message to Kafka for event {}", event);
        Message<TaskAssignmentEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader("contentType", "application/json")
                .setHeader(AppConstant.CORRELATION_ID, MDC.get(AppConstant.CORRELATION_ID))
                .setHeader(AppConstant.Authorization_Header,token)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
        log.info("Message sent to Kafka: {}", event);
    }
}
