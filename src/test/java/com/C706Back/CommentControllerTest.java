package com.C706Back;

import com.C706Back.controllers.CommentController;
import com.C706Back.models.builder.CommentListResponseBuilder;
import com.C706Back.models.builder.CommentResponseBuilder;
import com.C706Back.models.dto.response.CommentListResponse;
import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Pet;
import com.C706Back.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void verifyCommentListResponse() throws Exception {
        List<CommentResponse> commentResponses = Arrays.asList(DataComment.createComment001().orElseThrow(), DataComment.createComment002().orElseThrow());
        CommentListResponseBuilder commentListResponseBuilder = new CommentListResponseBuilder();
        CommentListResponse commentListResponse = commentListResponseBuilder
                .content(commentResponses)
                .pageNumber(0)
                .pageSize(10)
                .totalElements(2)
                .totalPages(1)
                .isLastPage(true)
                .build();
        when(commentService.listCommentsByPet(1L, 0, 10, "id", "asc")).thenReturn(commentListResponse);
        mockMvc.perform(get("/pets/{petId}/comments", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].message").value("Mensaje"))
                .andExpect(jsonPath("$.content[0].createdDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.content[0].updatedDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.content[0].pet.id").value("1"))
                .andExpect(jsonPath("$.content[1].message").value("Mensaje2"))
                .andExpect(jsonPath("$.content[1].createdDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.content[1].updatedDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.content[1].pet.id").value("1"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.lastPage").value(true));
        verify(commentService).listCommentsByPet(1L, 0, 10, "id", "asc");
    }

    @Test
    void verifyGetCommentById() throws Exception {
        when(commentService.getCommentById(1L, 1L))
                .thenReturn(DataComment.createComment001().orElseThrow());

        mockMvc.perform(get("/pets/{petId}/comments/{commentId}", 1L, 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Mensaje"))
                .andExpect(jsonPath("$.createdDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.updatedDate").value("2022-09-30T03:00:00.000+00:00"))
                .andExpect(jsonPath("$.pet.id").value("1"));
        verify(commentService).getCommentById(1L, 1L);
    }

    @Test
    void verifyCreateComment() throws Exception {
        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        Date date = new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime();
        Pet pet = new Pet();
        pet.setId(1L);
        CommentResponse commentResponse = commentResponseBuilder.id(1L)
                .message("Mensaje")
                .createdDate(date)
                .updatedDate(date)
                .pet(pet)
                .user(null)
                .build();

        mockMvc.perform(post("/pets/{petId}/comments", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentResponse)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void verifyUpdateComment() throws Exception {
        CommentResponseBuilder commentResponseBuilder = new CommentResponseBuilder();
        Date date = new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime();
        Pet pet = new Pet();
        pet.setId(1L);
        CommentResponse commentResponse = commentResponseBuilder.id(1L)
                .message("Mensaje")
                .createdDate(date)
                .updatedDate(date)
                .pet(pet)
                .user(null)
                .build();

        mockMvc.perform(put("/pets/{petId}/comments/{commentId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentResponse)))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void verifyDeleteComment() throws Exception {
        mockMvc.perform(delete("/pets/{petId}/comments/{commentId}", 1L, 1L))
                .andExpect(status().is(200))
                .andExpect(content().string("Comment was deleted"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
