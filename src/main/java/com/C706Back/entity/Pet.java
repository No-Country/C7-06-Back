package com.C706Back.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Getter @Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pure_race")
    private boolean pureRace;

    private int age;

    @JoinColumn(name = "id_location", unique = true)
    @ManyToOne
    private Location location;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;

    private String race;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "vaccinations_up_to_date")
    private boolean vaccinationsUpToDate;


}
