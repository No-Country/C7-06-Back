package com.C706Back.service;

import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.*;
import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;

import java.util.List;

public interface PetService {
    PetCardListResponse listPetsByUser(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir);

    PetCardListResponse listFilteredPets(AnimalType animalType, Gender gender, Integer startAge, Integer endAge, String race, String location, int pageNumber, int pageSize, String orderBy, String sortDir);

    List<Long> listPetIds(Long userId);
    PetProfileResponse createPet(Long userId, PetProfileRequest petProfileRequest);

    PetProfileResponse getPetById(Long petId);

    PetProfileResponse updatePet(Long petId, PetProfileRequest petProfileRequest);

    void deletePet(Long petId);
}
