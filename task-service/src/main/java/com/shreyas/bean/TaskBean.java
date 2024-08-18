package com.shreyas.bean;

import com.shreyas.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TaskBean {

    private UUID id;
    @NotNull(message = "Task title should not be null")
    private String title;
    @NotNull(message = "Task description should not be null")
    private String description;
    private String imageURL;
    private UUID assignedUserId;
    private TaskStatus status;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime deadLine;
    private LocalDateTime createdAt;
}
