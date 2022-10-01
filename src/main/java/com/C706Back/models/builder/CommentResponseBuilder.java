package com.C706Back.models.builder;

import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;

import java.util.Date;

public class CommentResponseBuilder implements IBuilder<CommentResponse> {

    private Long id;
    private String message;
    private Date createdDate;
    private Date updatedDate;
    private Pet pet;
    private User user;

    public CommentResponseBuilder() {
    }

    public CommentResponseBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CommentResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public CommentResponseBuilder createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public CommentResponseBuilder updatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public CommentResponseBuilder pet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public CommentResponseBuilder user(User user) {
        this.user = user;
        return this;
    }

    @Override
    public CommentResponse build() {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(id);
        commentResponse.setMessage(message);
        commentResponse.setCreatedDate(createdDate);
        commentResponse.setUpdatedDate(updatedDate);
        commentResponse.setPet(pet);
        commentResponse.setUser(user);
        return commentResponse;
    }
}