package com.C706Back;

import com.C706Back.models.entity.Comment;
import com.C706Back.repository.CommentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {
/*
    @Autowired
    private CommentRepository commentRepository;

    @Test
    //@Rollback(false)
    @Order(1)
    public void testSaveComment() {
        Comment comment = Comment.builder()
                .id(1L)
                .message("Mensaje")
                .createdDate(new Date())
                .updatedDate(new Date())
                .pet(null)
                .user(null)
                .build();

        Comment commentSaved = commentRepository.save(comment);
        assertNotNull(commentSaved);
    }

    @Test
    @Order(2)
    public void testFindById() {
        Long id = 1L;
        Comment comment = commentRepository.findById(id).orElse(null);
        assertThat(comment.getId()).isEqualTo(id);
    }

    @Test
    @Order(3)
    public void testFindCommentByIdNonExist() {
        Long id = 5L;
        Comment comment = commentRepository.findById(id).orElse(null);
        assertNull(comment);
    }

    @Test
    @Order(4)
    public void testSave() {
        Comment comment = Comment.builder()
                .id(1L)
                .message("Mensaje Actualizado")
                .createdDate(new Date())
                .updatedDate(new Date())
                .pet(null)
                .user(null)
                .build();

        Comment commentUpdated = commentRepository.save(comment);
        Comment commentDB = commentRepository.findById(1L).orElse(null);
        assertThat(commentUpdated.getMessage()).isEqualTo(commentDB.getMessage());
    }

    @Test
    public void testFindAll() {
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        assertThat(comments).size().isGreaterThan(0);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        boolean isExistentBeforeDelete = commentRepository.findById(id).isPresent();
        commentRepository.deleteById(id);
        boolean isExistentAfterDelete = commentRepository.findById(id).isPresent();
        assertTrue(isExistentBeforeDelete);
        assertFalse(isExistentAfterDelete);
    }

    */

}
