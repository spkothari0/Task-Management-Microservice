package com.shreyas.bean;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private String imageURL;
    private UUID assignedUserId;
    private TaskStatus status;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime deadLine;
    private LocalDateTime createdAt;
}
