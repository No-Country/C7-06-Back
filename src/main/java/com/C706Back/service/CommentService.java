package com.C706Back.service;

import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.dto.response.CommentListResponse;

public interface CommentService {

    CommentListResponse listCommentsByPet(Long petId, int pageNumber, int pageSize, String orderBy, String sortDir);

    CommentResponse createComment(Long petId, CommentResponse commentResponse);

    CommentResponse getCommentById(Long petId, Long commentId);

    CommentResponse updateComment(Long petId, Long commentId, CommentResponse commentResponse);

    void deleteComment(Long petId, Long commentId);
}
