package com.shreyas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Task ID must not be null")
    private UUID id;
    private String title;
    private String description;
    private String imageURL;
    @NotNull(message = "created by cannot be null")
    private UUID createdBy;
    private UUID modifiedBy;
    @NotNull(message = "modified by cannot be null")
    private UUID assignedUserId;
    @NotNull(message = "Status can not be null")
    private String status;
    @ElementCollection
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private List<String> tags;
    private LocalDateTime deadLine;
    @NotNull(message = "Created at can not be null")
    private LocalDateTime createdAt=LocalDateTime.now();
}
