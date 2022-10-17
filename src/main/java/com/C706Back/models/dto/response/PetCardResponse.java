package com.C706Back.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PetCardResponse {

    private Long id;
    private PictureResponse pictureResponse;
    private String animalType;
    private String name;
    private String race;
    private String gender;
    private int age;

}
