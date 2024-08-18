package com.shreyas.repository;

import com.shreyas.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    public List<Task> findByAssignedUserId(UUID userId);
}
