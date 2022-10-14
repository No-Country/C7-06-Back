package com.C706Back.service.impl;


import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.mapper.FavouriteCardMapper;
import com.C706Back.models.dto.request.FavouriteRequest;
import com.C706Back.models.dto.response.FavouriteCardListResponse;
import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.entity.Favourite;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;
import com.C706Back.repository.FavouriteRepository;
import com.C706Back.repository.PetRepository;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.FavouriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, UserRepository userRepository, PetRepository petRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @Override
    public FavouriteCardListResponse listFavouritesByUser(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Favourite> page = favouriteRepository.findByUserId(userId, pageable);
        List<Favourite> favouriteList = page.getContent();
        List<FavouriteCardResponse> favouriteCardResponseList = favouriteList.stream()
                .map(favourite -> {
                    return FavouriteCardMapper.mapToDto(favourite);
                }).collect(Collectors.toList());

        return FavouriteCardListResponse.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .content(favouriteCardResponseList)
                .build();
    }

    @Override
    public FavouriteCardResponse getFavouriteById(Long id) {
        Favourite favourite = favouriteRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Favourite", "id", id));
        return FavouriteCardMapper.mapToDto(favourite);
    }

    @Override
    public FavouriteCardResponse createFavourite(Long userId, Long petId) {
        System.out.println(petId);
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));

        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Favourite favourite = new Favourite();
        favourite.setFavourite(true);
        favourite.setPet(pet);
        favourite.setUser(user);
        Favourite favouriteInserted = favouriteRepository.save(favourite);
        return FavouriteCardMapper.mapToDto(favouriteInserted);
    }

    @Override
    public void deleteFavourite(Long favouriteId) {
        Favourite favourite = favouriteRepository
                .findById(favouriteId).orElseThrow(() -> new ResourceNotFoundException("Favourite", "id", favouriteId));
        favouriteRepository.delete(favourite);
    }
}
