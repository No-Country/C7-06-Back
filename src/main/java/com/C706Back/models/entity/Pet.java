package com.C706Back.models.entity;

import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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

    private String description;

    private boolean vaccinationsUpToDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "picture_id")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "pet")
    private List<Favourite> favourites;
    public Pet() {
    }
}
