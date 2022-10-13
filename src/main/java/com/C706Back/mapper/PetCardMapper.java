package com.C706Back.mapper;


import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.entity.Pet;

public class PetCardMapper {

    public static PetCardResponse mapToDto(Pet pet) {
        return PetCardResponse.builder()
                .petId(pet.getId())
                .name(pet.getName())
                .race(pet.getRace())
                .gender(pet.getRace().toString())
                .age(pet.getAge())
                .build();
    }
}
