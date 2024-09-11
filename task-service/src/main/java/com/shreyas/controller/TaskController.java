package com.shreyas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyas.bean.TaskBean;
import com.shreyas.bean.UserDto;
import com.shreyas.entity.TaskStatus;
import com.shreyas.service.interfaces.TaskService;
import com.shreyas.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class TaskController extends BaseController {
    private final TaskService taskService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public TaskController(TaskService taskService, UserService userService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/")
    @Operation(summary = "Create a new task for the authenticated user.",
            description = "Only ADMIN users can create the task."
    )
    public ResponseEntity<APIResponse<TaskBean>> createTask(@RequestBody TaskBean task, @RequestHeader("Authorization") String token) throws Exception {
        Object userResponse = userService.getUser(token);
        APIResponse<UserDto> user = getUserConverted(userResponse);
        if (user.getStatusCode() != 200) {
            throw new IllegalStateException("Invalid user token");
        }
        TaskBean result = taskService.createTask(task, user.getData().getId(), user.getData().getRole());
        return CreatedResponse("Task created successfully", result);

    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a task by its unique identifier")
    public ResponseEntity<APIResponse<TaskBean>> getTaskById(@PathVariable UUID id) throws Exception {
        TaskBean result = taskService.getTaskById(id);
        return SuccessResponse(result);
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get all tasks")
    public ResponseEntity<APIResponse<List<TaskBean>>> getAllTasks(@RequestParam(required = false) TaskStatus status) throws Exception {
        List<TaskBean> result = taskService.getAllTasks(status);
        return SuccessResponse(result);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update the task for the authenticated user.")
    public ResponseEntity<APIResponse<TaskBean>> updateTask(@PathVariable UUID id, @RequestBody TaskBean task, @RequestHeader("Authorization") String token) throws Exception {
        Object userResponse = userService.getUser(token);
        APIResponse<UserDto> user = getUserConverted(userResponse);
        if (user.getStatusCode() != 200) {
            throw new IllegalStateException("Invalid user token");
        }
        TaskBean result = taskService.updateTask(id, task, user.getData().getId());
        return SuccessResponse(result);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<APIResponse<Void>> deleteTask(@PathVariable UUID id) throws Exception {
        taskService.deleteTask(id);
        return SuccessResponseMessage("Task deleted successfully");
    }

    @PutMapping(value = "/{id}/user/{userid}/assigned")
    @Operation(summary = "Assign the task to the given user.")
    public ResponseEntity<APIResponse<TaskBean>> assignTaskToUser(@PathVariable UUID id, @PathVariable UUID userid, @RequestHeader("Authorization") String token) throws Exception {
        TaskBean result = taskService.assignTaskToUser(userid,id,token);
        return SuccessResponse(result);
    }

    @GetMapping(value = "/user")
    @Operation(summary = "Get the task associated with the given user.")
    public ResponseEntity<APIResponse<List<TaskBean>>> getAssignedUserTasks(@RequestParam(required = false) TaskStatus status, @RequestHeader("Authorization") String token) throws Exception {
        Object userResponse = userService.getUser(token);
        APIResponse<UserDto> user = getUserConverted(userResponse);
        if (user.getStatusCode() != 200) {
            throw new IllegalStateException("Invalid user token");
        }
        List<TaskBean> result = taskService.assignedUserTasks(user.getData().getId(), status);
        return SuccessResponse(result);
    }

    @PutMapping(value = "/{id}/complete")
    @Operation(summary = "Mark the task as complete.")
    public ResponseEntity<APIResponse<TaskBean>> markTaskAsComplete(@PathVariable UUID id) throws Exception {
        TaskBean result = taskService.completeTask(id);
        return SuccessResponse(result);
    }

    @PutMapping(value = "/{id}/cancel")
    @Operation(summary = "Cancel a task")
    public ResponseEntity<APIResponse<TaskBean>> cancelTask(@PathVariable UUID id) throws Exception {
        TaskBean result = taskService.cancelTask(id);
        return SuccessResponse(result);
    }

    private APIResponse<UserDto> getUserConverted(Object user) {
        APIResponse<UserDto> apiResponse;
        try {
            apiResponse = objectMapper.convertValue(user, objectMapper.getTypeFactory().constructParametricType(APIResponse.class, UserDto.class));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to convert response to APIResponse<UserDto>", e);
        }
        return apiResponse;
    }
}
