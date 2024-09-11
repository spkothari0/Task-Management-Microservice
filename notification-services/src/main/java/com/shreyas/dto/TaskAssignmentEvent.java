package com.shreyas.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskAssignmentEvent {
    private UUID taskId;
    private UUID userId;
}
