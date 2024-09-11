package com.shreyas.service.interfaces;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    public CompletableFuture<Boolean> sendEmail(String emailId, String Subject, String Message);
}
