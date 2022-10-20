package com.C706Back.models.dto.response;

import com.C706Back.models.entity.Picture;
import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.models.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PetProfileResponse {

    private Long id;

    private Long userId;

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

    private List<PictureResponse> pictures;

    private Date updatedDate;

}
