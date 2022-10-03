package com.C706Back.controllers;

import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.dto.response.CommentListResponse;
import com.C706Back.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @RequestMapping(path = "/pets/{petId}/comments", method = RequestMethod.GET)
    private CommentListResponse listCommentsByPet(@PathVariable(value = "petId") Long petId,
                                                  @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = "id", required = false) String orderBy,
                                                  @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return commentService.listCommentsByPet(petId, pageNumber, pageSize, orderBy, sortDir);
    }

    @RequestMapping(path = "/pets/{petId}/comments/{commentId}", method = RequestMethod.GET)
    private ResponseEntity<CommentResponse> getCommentById(@PathVariable(value = "petId") Long petId, @PathVariable(value = "commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(petId, commentId));
    }

    @RequestMapping(path = "/pets/{petId}/comments/{commentId}", method = RequestMethod.PUT)
    private ResponseEntity<CommentResponse> updateComment(@PathVariable(value = "petId") Long petId, @PathVariable(value = "commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(petId, commentId, commentRequest));
    }

    @RequestMapping(path = "/pets/{petId}/comments", method = RequestMethod.POST)
    private ResponseEntity<CommentResponse> createComment(@PathVariable(value = "petId") Long petId, @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.createComment(petId, commentRequest), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/pets/{petId}/comments/{commentId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteComment(@PathVariable(value = "petId") Long petId, @PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(petId, commentId);
        return new ResponseEntity<>("Comment was deleted", HttpStatus.OK);
    }
}
