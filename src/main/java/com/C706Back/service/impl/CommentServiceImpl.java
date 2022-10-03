package com.C706Back.service.impl;

import com.C706Back.exception.BadRequestException;
import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.mapper.CommentRequestMapper;
import com.C706Back.mapper.CommentResponseMapper;
import com.C706Back.models.builder.CommentListResponseBuilder;
import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.dto.response.CommentListResponse;
import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;
import com.C706Back.repository.CommentRepository;
import com.C706Back.repository.PetRepository;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    CommentResponseMapper commentResponseMapper = new CommentResponseMapper();
    CommentRequestMapper commentRequestMapper = new CommentRequestMapper();

    public CommentServiceImpl(CommentRepository commentRepository, PetRepository petRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentListResponse listCommentsByPet(Long petId, int pageNumber, int pageSize, String orderBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Comment> page = commentRepository.findByPetId(petId, pageable);
        List<Comment> comments = page.getContent();
        List<CommentResponse> content = comments.stream()
                .map(commentResponseMapper::map).collect(Collectors.toList());

        CommentListResponseBuilder commentListResponseBuilder = new CommentListResponseBuilder();
        CommentListResponse commentsResponse = commentListResponseBuilder
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .build();
        return commentsResponse;
    }

    @Override
    public CommentResponse createComment(Long petId, CommentRequest commentRequest) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        User user = userRepository
                .findById(commentRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", commentRequest.getUserId()));
        Comment comment = commentRequestMapper.map(commentRequest, user);
        comment.setCreatedDate(new Date());
        comment.setUpdatedDate(new Date());
        comment.setPet(pet);
        Comment commentInserted = commentRepository.save(comment);
        return commentResponseMapper.map(commentInserted);
    }

    @Override
    public CommentResponse getCommentById(Long petId, Long commentId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        Comment comment = commentRepository
                .findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPet().getId().equals(pet.getId()))
            throw new BadRequestException("The comment doesn't belong to the pet profile");
        return commentResponseMapper.map(comment);
    }

    @Override
    public CommentResponse updateComment(Long petId, Long commentId, CommentRequest commentRequest) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        Comment comment = commentRepository
                .findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPet().getId().equals(pet.getId()))
            throw new BadRequestException("The comment doesn't belong to the pet profile");
        if (!comment.getUser().getId().equals(commentRequest.getUserId()))
            throw new BadRequestException("The comment doesn't match the user");
        comment.setMessage(commentRequest.getMessage());
        comment.setUpdatedDate(new Date());
        Comment commentUpdated = commentRepository.save(comment);
        return commentResponseMapper.map(commentUpdated);
    }

    @Override
    public void deleteComment(Long petId, Long commentId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        Comment comment = commentRepository
                .findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPet().getId().equals(pet.getId()))
            throw new BadRequestException("The comment doesn't belong to the pet profile");
        commentRepository.delete(comment);
    }
}
