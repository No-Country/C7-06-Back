package com.C706Back;

import com.C706Back.models.builder.CommentBuilder;
import com.C706Back.models.builder.CommentRequestBuilder;
import com.C706Back.models.dto.request.CommentRequest;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;
import com.C706Back.repository.CommentRepository;
import com.C706Back.repository.PetRepository;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.CommentService;
import com.C706Back.service.impl.CommentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private UserRepository userRepository;
    private CommentService commentService;

    Pet pet;

    User user;
    Comment comment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, petRepository, userRepository);
        CommentBuilder commentBuilder = new CommentBuilder();
        pet = new Pet();
        pet.setId(1L);
        user = new User();
        user.setId(1L);
        comment = commentBuilder.id(1L)
                .message("Mensaje")
                .createdDate(new Date())
                .updatedDate(new Date())
                .pet(pet)
                .user(user)
                .build();
    }

    @Test
    void testListCommentsByPet() {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Comment> page = new PageImpl<>(List.of(comment), pageable, 0);
        Mockito.when(commentRepository.findByPetId(1L, pageable)).thenReturn(page);
        assertNotNull(commentService.listCommentsByPet(1L, 0, 10, "id", "asc"));
    }

    @Test
    void testGetCommentById() {
        Mockito.when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));
        Mockito.when(commentRepository.findById(1L))
                .thenReturn(Optional.of(comment));
        CommentResponse found = commentService.getCommentById(1L, 1L);
        Assertions.assertThat(found.getMessage()).isEqualTo("Mensaje");
    }

    @Test
    void testCreateComment() {
        CommentRequestBuilder commentRequestBuilder = new CommentRequestBuilder();
        CommentRequest commentRequest = commentRequestBuilder
                .userId(1L)
                .message("Mensaje")
                .build();
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        Mockito.when(petRepository.findById(1L))
                .thenReturn(Optional.of(pet));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);
        assertNotNull(commentService.createComment(1L, commentRequest));
    }


}
