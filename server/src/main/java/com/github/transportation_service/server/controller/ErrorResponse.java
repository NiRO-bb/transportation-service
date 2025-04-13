package com.github.transportation_service.server.controller;

import java.util.List;

public class ErrorResponse {
    private List<String> messages;
    private int status;

    public ErrorResponse() {}

    public ErrorResponse(List<String> messages, int status) {
        this.messages = messages;
        this.status = status;
    }

    public List<String> getMessage() {
        return messages;
    }

    public void setMessage(List<String> message) {
        this.messages = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
