package com.C706Back.mapper;


import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PetCardMapper {

    public static PetCardResponse mapToDto(Pet pet) {
        List<Picture> pictures = pet.getPictures();
        PictureResponse pictureResponse = null;
        if (!pictures.isEmpty()) {
            pictureResponse = new PictureResponse(pictures.get(0).getId(), pictures.get(0).getPath());
        }
        return PetCardResponse.builder()
                .petId(pet.getId())
                .name(pet.getName())
                .race(pet.getRace())
                .gender(pet.getGender().toString())
                .age(pet.getAge())
                .pictureResponse(pictureResponse)
                .build();
    }
}
