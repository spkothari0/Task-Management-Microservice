package com.shreyas.repository;

import com.shreyas.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepo extends JpaRepository<Submission, UUID> {
    List<Submission> findByTaskId(UUID taskId);
}
