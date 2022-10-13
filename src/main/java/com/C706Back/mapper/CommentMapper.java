package com.C706Back.mapper;

import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.User;

public class CommentMapper {

    public static Comment mapToEntity(CommentRequest commentRequest, User user) {
        return Comment.builder()
                .message(commentRequest.getMessage())
                .user(user)
                .build();
    }

    public static CommentResponse mapToDto(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .updatedDate(comment.getUpdatedDate())
                .message(comment.getMessage())
                .fullName(comment.getUser().getName() + " " + comment.getUser().getSurname())
                .avatar(comment.getUser().getPicture())
                .build();
    }
}
