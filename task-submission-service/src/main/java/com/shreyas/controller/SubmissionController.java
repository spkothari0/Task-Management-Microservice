package com.shreyas.controller;

import com.shreyas.bean.TaskDto;
import com.shreyas.bean.UserDto;
import com.shreyas.entity.Submission;
import com.shreyas.service.interfaces.SubmissionService;
import com.shreyas.service.interfaces.TaskService;
import com.shreyas.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/submission", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class SubmissionController extends BaseController{

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<APIResponse<Submission>> submitTask(@RequestParam UUID taskId, @RequestParam String repoLink, @RequestHeader("Authorization") String token) throws Exception {
        APIResponse<UserDto> userResponse = userService.getUser(token);
        Submission submission=submissionService.submitTask(taskId, repoLink,userResponse.getData().getId());
        return CreatedResponse("Successfully completed submission", submission);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<APIResponse<Submission>> getTaskSubmissionById(@PathVariable UUID id) throws Exception {
        Submission submission = submissionService.getTaskSubmissionById(id);
        return SuccessResponse(submission);
    }

    @GetMapping(value = "/")
    public ResponseEntity<APIResponse<List<Submission>>> getAllTaskSubmissions() {
        List<Submission> submissions = submissionService.getAllTaskSubmissions();
        return SuccessResponse(submissions);
    }

    @GetMapping(value = "/task/{taskId}")
    public ResponseEntity<APIResponse<List<Submission>>> getAllTaskSubmissionsByTaskId(@PathVariable UUID taskId) {
        List<Submission> submissions = submissionService.getAllTaskSubmissionsByTaskId(taskId);
        return SuccessResponse(submissions);
    }

    @PutMapping(value = "/{id}/accept")
    public ResponseEntity<APIResponse<Submission>> acceptOrDeclineSubmission(@PathVariable UUID id,@RequestParam("accept") boolean accept) throws Exception {
        Submission submission = submissionService.acceptOrDeclineSubmission(id, accept);
        return SuccessResponse(submission);
    }
}
