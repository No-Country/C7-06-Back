package com.C706Back.models.entity;

import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean pureRace;

    private int age;

    @Enumerated(value = EnumType.STRING)
    private AnimalType animalType;

    private String race;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private boolean vaccinationsUpToDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pet")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "pet")
    private List<Favourite> favourites;

    @OneToMany(mappedBy = "pet")
    private List<Comment> comments = new ArrayList<>();

    public Pet() {}
}
