package com.C706Back.models.builder;

import com.C706Back.models.entity.Comment;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;

import java.util.Date;

public class CommentBuilder implements IBuilder<Comment> {

    private Long id;
    private String message;
    private Date createdDate;
    private Date updatedDate;
    private Pet pet;
    private User user;

    public CommentBuilder() {

    }

    public CommentBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CommentBuilder message(String message) {
        this.message = message;
        return this;
    }

    public CommentBuilder createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public CommentBuilder updatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public CommentBuilder pet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public CommentBuilder user(User user) {
        this.user = user;
        return this;
    }

    @Override
    public Comment build() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setMessage(message);
        comment.setCreatedDate(createdDate);
        comment.setUpdatedDate(updatedDate);
        comment.setPet(pet);
        comment.setUser(user);
        return comment;
    }
}
