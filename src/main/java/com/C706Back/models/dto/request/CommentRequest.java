package com.C706Back.models.dto.request;

public class CommentRequest {
    private Long userId;
    private String message;

    public CommentRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
