package com.C706Back.service.impl;


import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.mapper.FavouriteCardMapper;
import com.C706Back.mapper.PetCardMapper;
import com.C706Back.models.dto.response.FavouriteCardListResponse;
import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.dto.response.PetCardListResponse;
import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.entity.Favourite;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;
import com.C706Back.models.enums.Gender;
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
import java.util.Optional;
import java.util.Set;
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
                    return FavouriteCardMapper.mapToDto(favourite, userId);
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
    public PetCardListResponse listSuggestedPets(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir) {

        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Pet> userPets = user.getPets();

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Pet> page = null;

        if (userPets.isEmpty()) {
            page = petRepository.findAllByFilter(null, null, null, null, null, null, pageable);
        } else {
            Pet userPet = userPets.get(0);
            Gender oppositeGender = userPet.getGender().equals(Gender.MALE) ? Gender.FEMALE : Gender.MALE;
            int userPetAge = userPet.getAge();
            int startAge = 0;
            int endAge = 0;
            if (userPetAge > 0 && userPetAge <= 3) {
                startAge = 0;
                endAge = 3;
            }

            if (userPetAge > 3 && userPetAge <= 6) {
                startAge = 0;
                endAge = 3;
            }

            if (userPetAge > 6) {
                startAge = 0;
                endAge = 3;
            }

            page = petRepository.findAllByFilter(userPet.getAnimalType(), oppositeGender, startAge, endAge, userPet.getRace(), null, pageable);

            if (page.getContent().size() == 0) {
                page = petRepository.findAllByFilter(userPet.getAnimalType(), oppositeGender, startAge, endAge, null, null, pageable);
            }
        }

        List<Pet> petList = page.getContent();

        if (!petList.isEmpty()) {

            petList = petList.stream()
                    .filter(pet -> {
                        return pet.getUser().getId() != userId;
                    }).collect(Collectors.toList());

            List<PetCardResponse> petCardResponseList = petList.stream()
                    .map(pet -> PetCardMapper.mapToDto(pet, pet.getUser().getId())).collect(Collectors.toList());

            return PetCardListResponse.builder()
                    .pageNumber(page.getNumber())
                    .pageSize(page.getSize())
                    .totalElements(page.getTotalElements())
                    .totalPages(page.getTotalPages())
                    .isLastPage(page.isLast())
                    .content(petCardResponseList)
                    .build();
        }

        return null;

    }

    @Override
    public boolean getIfIsFavourite(Long userId, Long petId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));

        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Favourite> favourites = user.getFavourites();

        if (favourites != null && !favourites.isEmpty()) {

            Optional<Favourite> favouriteOptional = favourites.stream()
                    .filter(favourite -> favourite.getPet().equals(pet))
                    .findFirst();

            if (favouriteOptional.isPresent() && favouriteOptional.get().isFavourite())
                return true;
        }

        return false;
    }

    @Override
    public FavouriteCardResponse createFavourite(Long userId, Long petId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));

        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Favourite> favourites = user.getFavourites();
        Favourite favouriteInserted = null;

        if (favourites != null || !favourites.isEmpty()) {

            Favourite favouriteFinded = favourites.stream()
                    .filter(favourite -> favourite.getPet().equals(pet))
                    .findFirst().orElse(null);

            if (favouriteFinded != null && favouriteFinded.isFavourite()) {
                favouriteFinded.setFavourite(false);
            } else {
                favouriteFinded.setFavourite(true);
            }


            if (favouriteFinded != null)
                favouriteInserted = favouriteRepository.save(favouriteFinded);
        }

        if (favourites == null || favourites.isEmpty()) {
            Favourite favourite = new Favourite();
            favourite.setFavourite(true);
            favourite.setPet(pet);
            favourite.setUser(user);
            System.out.println("AQUI");
            favouriteInserted = favouriteRepository.save(favourite);
            System.out.println("AQUI TMB");
        }
        return FavouriteCardMapper.mapToDto(favouriteInserted, userId);
    }

    @Override
    public void deleteFavourite(Long favouriteId) {
        Favourite favourite = favouriteRepository
                .findById(favouriteId).orElseThrow(() -> new ResourceNotFoundException("Favourite", "id", favouriteId));
        favouriteRepository.delete(favourite);
    }
}
