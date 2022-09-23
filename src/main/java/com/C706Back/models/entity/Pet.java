package com.C706Back.models.entity;

import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean pureRace;

    private int age;

    @ManyToOne
    @JoinColumn(name = "id_location", unique = true)
    private Location location;

    @Enumerated(value = EnumType.STRING)
    private AnimalType animalType;

    private String race;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private boolean vaccinationsUpToDate;

    public Pet() {}
}
