package com.C706Back.service;

import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.dto.response.CommentListResponse;

public interface CommentService {

    CommentListResponse listCommentsByPet(Long petId, int pageNumber, int pageSize, String orderBy, String sortDir);

    CommentResponse createComment(Long petId, Long userId, CommentRequest commentRequest);

    CommentResponse getCommentById(Long commentId);

    CommentResponse updateComment(Long commentId, Long userId, CommentRequest commentRequest);

    void deleteComment(Long commentId);
}
