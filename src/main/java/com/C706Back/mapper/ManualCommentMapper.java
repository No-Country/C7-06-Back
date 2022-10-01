package com.C706Back.mapper;

import com.C706Back.models.builder.CommentBuilder;
import com.C706Back.models.builder.CommentResponseBuilder;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Comment;

public class ManualCommentMapper extends ManualMapper<CommentResponse, Comment> {

    @Override
    public CommentResponse mapEntityToDTO(Comment comment) {
        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        CommentResponse commentResponse = commentResponseBuilder
                .id(comment.getId())
                .message(comment.getMessage())
                .createdDate(comment.getCreatedDate())
                .updatedDate(comment.getUpdatedDate())
                .pet(comment.getPet())
                .user(comment.getUser())
                .build();
        return commentResponse;
    }

    @Override
    public Comment mapDTOtoEntity(CommentResponse dto) {
        CommentBuilder commentBuilder = new CommentBuilder();
        Comment comment = commentBuilder
                .id(dto.getId())
                .message(dto.getMessage())
                .createdDate(dto.getCreatedDate())
                .updatedDate(dto.getUpdatedDate())
                .pet(dto.getPet())
                .user(dto.getUser())
                .build();
        return comment;
    }
}
