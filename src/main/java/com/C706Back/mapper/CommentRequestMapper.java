package com.C706Back.mapper;

import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.User;

public class CommentRequestMapper {
    public Comment map(CommentRequest commentRequest, User user) {
        Comment comment = new Comment();
        comment.setMessage(commentRequest.getMessage());
        comment.setUser(user);
        return comment;
    }
}
