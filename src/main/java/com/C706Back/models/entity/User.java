package com.C706Back.models.entity;

import com.C706Back.models.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @NotEmpty(message = "The surname cannot be empty")
    private String surname;

    @NotEmpty(message = "The email cannot be empty")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "The password cannot be empty")
    private String password;

    @NotEmpty(message = "The address cannot be empty")
    private String address;

    @NotEmpty(message = "The description cannot be empty")
    private String description;

    @NotEmpty(message = "The phone number cannot be empty")
    private String phoneNumber;

    @NotEmpty(message = "The role cannot be empty")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @OneToMany(mappedBy = "user")
    private List<Favourite> favourites;

    @OneToMany(mappedBy = "user")
    private List<Pet> pets;
    public User() {
    }

}
