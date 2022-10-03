package com.C706Back.models.builder;

import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Picture;

import java.util.Date;

public class CommentResponseBuilder implements IBuilder<CommentResponse> {
    private Long commentId;
    private Long userId;
    private Date updatedDate;
    private String message;
    private String username;
    private Picture avatar;

    public CommentResponseBuilder() {
    }

    public CommentResponseBuilder commentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public CommentResponseBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CommentResponseBuilder updatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public CommentResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public CommentResponseBuilder username(String username) {
        this.username = username;
        return this;
    }

    public CommentResponseBuilder avatar(Picture avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public CommentResponse build() {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(commentId);
        commentResponse.setUserId(userId);
        commentResponse.setUpdatedDate(updatedDate);
        commentResponse.setMessage(message);
        commentResponse.setUsername(username);
        commentResponse.setAvatar(avatar);
        return commentResponse;
    }
}