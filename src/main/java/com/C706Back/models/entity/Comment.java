package com.C706Back.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The message cannot be empty")
    @Size(max = 500, message = "The message could not exceed 500 characters")
    @Column(columnDefinition="varchar(500)")
    private String message;

    private Date createdDate;

    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
    }
}
