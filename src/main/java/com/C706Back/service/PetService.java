package com.C706Back.service;

import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.*;
import jdk.jfr.ContentType;

public interface PetService {

    PetProfileResponse createPet(Long userId, PetProfileRequest petProfileRequest);
    PetProfileResponse getPetById(Long petId);

    PetProfileResponse updatePet(Long petId, PetProfileRequest petProfileRequest);

    void deletePet(Long petId);

    PetCardListResponse listPetsByUser(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir);

    /*PetCardListResponse listPetsByLastUpdate(ContentType contentType, int pageNumber, int pageSize, String orderBy, String sortDir);*/

}
