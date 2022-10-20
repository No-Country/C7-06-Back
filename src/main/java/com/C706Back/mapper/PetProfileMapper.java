package com.C706Back.mapper;

import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.PetProfileResponse;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Location;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.Picture;
import com.C706Back.models.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<PictureResponse> pictureResponseList = new ArrayList<>();
        if (pet.getPictures() != null) {
            pictureResponseList = pet.getPictures().stream()
                    .map(picture -> {
                        return new PictureResponse(picture.getId(), picture.getPath());
                    }).collect(Collectors.toList());
        }
        return PetProfileResponse.builder()
                .id(pet.getId())
                .userId(pet.getUser().getId())
                .name(pet.getName())
                .age(pet.getAge())
                .location(pet.getLocation().getName())
                .description(pet.getDescription())
                .animalType(pet.getAnimalType())
                .race(pet.getRace())
                .gender(pet.getGender())
                .weight(pet.getWeight())
                .size(pet.getSize())
                .updatedDate(pet.getUpdatedDate())
                .vaccinationsUpToDate(pet.isVaccinationsUpToDate())
                .pureRace(pet.isPureRace())
                .pictures(pictureResponseList)
                .build();
    }
}
