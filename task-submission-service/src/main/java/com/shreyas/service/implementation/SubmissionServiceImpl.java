package com.shreyas.service.implementation;

import com.shreyas.bean.TaskDto;
import com.shreyas.controller.APIResponse;
import com.shreyas.entity.Submission;
import com.shreyas.entity.SubmissionStatus;
import com.shreyas.repository.SubmissionRepo;
import com.shreyas.service.interfaces.SubmissionService;
import com.shreyas.service.interfaces.TaskService;
import com.shreyas.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepo submissionRepo;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;


    /**
     * @param taskId
     * @param repoLink
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Submission submitTask(UUID taskId, String repoLink, UUID userId) throws Exception {
        APIResponse<TaskDto> task=taskService.getTaskById(taskId);
        if(task.getData() != null){
            Submission submission=new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setRepoLink(repoLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepo.save(submission);
        }
        throw new Exception("Task not found with id: "+taskId);
    }

    /**
     * @param submissionId
     * @return
     */
    @Override
    public Submission getTaskSubmissionById(UUID submissionId) throws Exception {
        return submissionRepo.findById(submissionId).orElseThrow(()-> new Exception("Task submission not found with id: " + submissionId));
    }

    /**
     * @return
     */
    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepo.findAll();
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    public List<Submission> getAllTaskSubmissionsByTaskId(UUID taskId) {
        return submissionRepo.findByTaskId(taskId);
    }

    /**
     * @param submissionId
     * @param accept
     * @return
     * @throws Exception
     */
    @Override
    public Submission acceptOrDeclineSubmission(UUID submissionId, boolean accept) throws Exception {
        Submission submission = getTaskSubmissionById(submissionId);
        submission.setStatus(accept? SubmissionStatus.ACCEPTED.name() : SubmissionStatus.REJECTED.name());
        if(accept)
            taskService.markTaskAsComplete(submission.getTaskId());
        return submissionRepo.save(submission);
    }
}
