package com.shreyas.dto;

public enum TaskStatus {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    TaskStatus(String status) {

    }
}
