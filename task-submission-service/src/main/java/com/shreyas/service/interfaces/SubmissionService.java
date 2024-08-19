package com.shreyas.service.interfaces;

import com.shreyas.entity.Submission;

import java.util.List;
import java.util.UUID;

public interface SubmissionService {
    Submission submitTask(UUID taskId, String repoLink, UUID userId) throws Exception;
    Submission getTaskSubmissionById(UUID submissionId) throws Exception;
    List<Submission> getAllTaskSubmissions();
    List<Submission> getAllTaskSubmissionsByTaskId(UUID taskId);
    Submission acceptOrDeclineSubmission(UUID submissionId, boolean accept) throws Exception;
}
