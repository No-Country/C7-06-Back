package com.C706Back.mapper;

import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.PetProfileResponse;
import com.C706Back.models.entity.Location;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;

public class PetProfileMapper {

    public static Pet mapToEntity(PetProfileRequest petProfileRequest, User user, Location location) {
        return Pet.builder()
                .name(petProfileRequest.getName())
                .age(petProfileRequest.getAge())
                .location(location)
                .description(petProfileRequest.getDescription())
                .animalType(petProfileRequest.getAnimalType())
                .race(petProfileRequest.getRace())
                .gender(petProfileRequest.getGender())
                .weight(petProfileRequest.getWeight())
                .size(petProfileRequest.getSize())
                .vaccinationsUpToDate(petProfileRequest.isVaccinationsUpToDate())
                .pureRace(petProfileRequest.isPureRace())
                .user(user)
                .build();
    }

    public static PetProfileResponse mapToDto(Pet pet) {
        return PetProfileResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .location(pet.getLocation().getName())
                .description(pet.getDescription())
                .animalType(pet.getAnimalType())
                .race(pet.getRace())
                .gender(pet.getGender())
                .weight(pet.getWeight())
                .size(pet.getSize())
                .vaccinationsUpToDate(pet.isVaccinationsUpToDate())
                .pureRace(pet.isPureRace())
                .pictures(pet.getPictures())
                .build();
    }
}
