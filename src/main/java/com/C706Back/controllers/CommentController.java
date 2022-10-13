package com.C706Back.controllers;

import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentListResponse;
import com.C706Back.models.enums.Role;
import com.C706Back.service.CommentService;
import com.C706Back.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    private final JwtUtils jwtUtils;

    @RequestMapping(path = "/pets/{petId}/comments", method = RequestMethod.GET)
    private CommentListResponse listCommentsByPet(@PathVariable(value = "petId") Long petId,
                                                  @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String orderBy,
                                                  @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        return commentService.listCommentsByPet(petId, pageNumber, pageSize, orderBy, sortDir);
    }

    @RequestMapping(path = "/pets/comments/{commentId}", method = RequestMethod.GET)
    private ResponseEntity<?> getCommentById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "commentId") Long commentId) throws Exception {

        return ResponseEntity.ok(commentService.getCommentById(commentId));

    }

    @RequestMapping(path = "/pets/comments/{commentId}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "commentId") Long commentId, @RequestBody CommentRequest commentRequest) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        Long userId = jwtUtils.getUserId(token);

        return ResponseEntity.ok(commentService.updateComment(commentId, userId, commentRequest));
    }

    @RequestMapping(path = "/pets/{petId}/comments", method = RequestMethod.POST)
    private ResponseEntity<?> createComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "petId") Long petId, @RequestBody CommentRequest commentRequest) throws Exception {
        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        Long userId = jwtUtils.getUserId(token);

        return new ResponseEntity<>(commentService.createComment(petId, userId, commentRequest), HttpStatus.CREATED);

    }

    @RequestMapping(path = "/pets/comments/{commentId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "commentId") Long commentId) throws Exception {
        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);
        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment was deleted", HttpStatus.OK);
    }
}
