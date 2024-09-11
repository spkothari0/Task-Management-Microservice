package com.shreyas.service.interfaces;

import com.shreyas.bean.TaskBean;
import com.shreyas.entity.Task;
import com.shreyas.entity.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskBean createTask(TaskBean task,UUID author ,String requesterRole) throws Exception;

    TaskBean getTaskById(UUID id) throws Exception;

    List<TaskBean> getAllTasks(TaskStatus status) throws Exception;

    TaskBean updateTask(UUID id, TaskBean task, UUID userId) throws Exception;

    void deleteTask(UUID taskId) throws Exception;

    TaskBean assignTaskToUser(UUID userId, UUID taskId, String token) throws Exception;

    List<TaskBean> assignedUserTasks(UUID userId, TaskStatus status) throws Exception;

    TaskBean completeTask(UUID taskId) throws Exception;

    TaskBean cancelTask(UUID taskId) throws Exception;


}
