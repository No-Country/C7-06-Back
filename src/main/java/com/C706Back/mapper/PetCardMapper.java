package com.C706Back.mapper;


import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.Picture;

import java.util.List;

public class PetCardMapper {

    public static PetCardResponse mapToDto(Pet pet, Long userId) {
        List<Picture> pictures = pet.getPictures();
        PictureResponse pictureResponse = null;
        if (!pictures.isEmpty()) {
            pictureResponse = new PictureResponse(pictures.get(0).getId(), pictures.get(0).getPath());
        }
        return PetCardResponse.builder()
                .id(pet.getId())
                .userId(userId)
                .name(pet.getName())
                .animalType(pet.getAnimalType().toString())
                .race(pet.getRace())
                .gender(pet.getGender().toString())
                .age(pet.getAge())
                .pictureResponse(pictureResponse)
                .build();
    }
}
