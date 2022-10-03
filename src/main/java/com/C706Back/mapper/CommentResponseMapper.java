package com.C706Back.mapper;

import com.C706Back.models.builder.CommentResponseBuilder;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Comment;

public class CommentResponseMapper {
    public CommentResponse map(Comment comment) {
        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        CommentResponse commentResponse = commentResponseBuilder
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .updatedDate(comment.getUpdatedDate())
                .message(comment.getMessage())
                .username(comment.getUser().getUsername())
                .avatar(comment.getUser().getPicture())
                .build();
        return commentResponse;
    }
}
