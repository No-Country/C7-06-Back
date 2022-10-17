package com.C706Back.service;

import com.C706Back.models.dto.request.FavouriteRequest;
import com.C706Back.models.dto.response.FavouriteCardListResponse;
import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.dto.response.PetCardListResponse;

public interface FavouriteService {

    FavouriteCardListResponse listFavouritesByUser(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir);

    PetCardListResponse listSuggestedPets(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir);

    boolean getIfIsFavourite(Long userId, Long petId);

    FavouriteCardResponse createFavourite(Long userId, Long petId);

    void deleteFavourite(Long favouriteId);

}
