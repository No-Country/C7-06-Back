package com.C706Back.service.impl;


import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.mapper.FavouriteCardMapper;
import com.C706Back.mapper.PetCardMapper;
import com.C706Back.models.dto.request.FavouriteRequest;
import com.C706Back.models.dto.response.FavouriteCardListResponse;
import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.dto.response.PetCardListResponse;
import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.entity.Favourite;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.User;
import com.C706Back.models.enums.AnimalType;
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



        List<Favourite> favourites = user.getFavourites();

        AnimalType averageAnimalType = null;
        Gender suggestedGender = null;

        if (favourites != null || !favourites.isEmpty()) {
            List<Pet> pets = favourites.stream()
                    .map(Favourite::getPet).collect(Collectors.toList());

            Set<Pet> cats = pets.stream()
                    .filter(pet -> {
                        return pet.getAnimalType().equals(AnimalType.CAT);
                    }).collect(Collectors.toSet());

            Set<Pet> dogs = pets.stream()
                    .filter(pet -> {
                        return pet.getAnimalType().equals(AnimalType.DOG);
                    }).collect(Collectors.toSet());

            averageAnimalType = cats.size() > dogs.size() ? AnimalType.CAT : AnimalType.DOG;


            AnimalType finalAverageAnimalType = averageAnimalType;
            Set<Pet> males = pets.stream()
                    .filter(pet -> {
                        return pet.getGender().equals(Gender.MALE) && pet.getAnimalType().equals(finalAverageAnimalType);
                    }).collect(Collectors.toSet());

            Set<Pet> females = pets.stream()
                    .filter(pet -> {
                        return pet.getGender().equals(Gender.FEMALE) && pet.getAnimalType().equals(finalAverageAnimalType);
                    }).collect(Collectors.toSet());

            suggestedGender = males.size() > females.size() ? Gender.FEMALE : Gender.MALE;

        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Pet> page = petRepository.findAllByFilter(averageAnimalType, suggestedGender, null, null, null, null, pageable);

        List<Pet> petList = page.getContent();

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
