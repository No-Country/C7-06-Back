package com.C706Back.models.builder;

import com.C706Back.models.dto.request.CommentRequest;

public class CommentRequestBuilder implements IBuilder<CommentRequest> {

    private Long userId;
    private String message;

    public CommentRequestBuilder() {
    }

    public CommentRequestBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CommentRequestBuilder message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public CommentRequest build() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setUserId(userId);
        commentRequest.setMessage(message);
        return commentRequest;
    }
}
