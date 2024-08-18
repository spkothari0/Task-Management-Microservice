package com.shreyas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private UUID id;

    private String title;
    private String description;
    private String imageURL;
    @NotNull
    private UUID createdBy;
    private UUID modifiedBy;
    @NotNull
    private UUID assignedUserId;
    @NotNull
    private TaskStatus status;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime deadLine;
    @NotNull
    private LocalDateTime createdAt=LocalDateTime.now();
}
