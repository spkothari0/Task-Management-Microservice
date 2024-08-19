package com.shreyas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Submission ID must not be null")
    private UUID id;

    @NotNull(message = "Task ID must not be null")
    private UUID taskId;

    private  String repoLink;

    @NotNull(message = "User Id must not be null")
    private UUID userId;

    @NotNull(message = "Status must not be null")
    private String status=SubmissionStatus.PENDING.name();

    @NotNull(message = "Submission time cannot be null")
    private LocalDateTime submissionTime=LocalDateTime.now();
}
