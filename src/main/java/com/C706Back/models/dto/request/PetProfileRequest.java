package com.C706Back.models.dto.request;

import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PetProfileRequest {

    private String name;

    private int age;

    private String location;

    private String description;

    private AnimalType animalType;

    private String race;

    private Gender gender;

    private double weight;

    private Size size;

    private boolean vaccinationsUpToDate;

    private boolean pureRace;
}
