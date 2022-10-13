package com.C706Back.models.entity;

import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @javax.validation.constraints.Size(max = 500, message = "The message could not exceed 500 characters")
    @Column(columnDefinition="varchar(500)")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private AnimalType animalType;

    private String race;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private double weight;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    private boolean vaccinationsUpToDate;

    private boolean pureRace;

    private Date createdDate;

    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pet")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "pet")
    private List<Favourite> favourites;
    public Pet() {
    }
}
