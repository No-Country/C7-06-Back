package com.C706Back.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private String name;

    private String password;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

}
