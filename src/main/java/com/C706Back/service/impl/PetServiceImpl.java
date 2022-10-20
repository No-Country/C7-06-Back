package com.C706Back.service.impl;

import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.mapper.PetCardMapper;
import com.C706Back.mapper.PetProfileMapper;
import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.PetCardListResponse;
import com.C706Back.models.dto.response.PetCardResponse;
import com.C706Back.models.dto.response.PetProfileResponse;
import com.C706Back.models.entity.*;
import com.C706Back.models.enums.AnimalType;
import com.C706Back.models.enums.Gender;
import com.C706Back.repository.LocationRepository;
import com.C706Back.repository.PetRepository;
import com.C706Back.repository.PictureRepository;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    private final PictureRepository pictureRepository;

    private final LocationRepository locationRepository;

    public PetServiceImpl(UserRepository userRepository, PetRepository petRepository, PictureRepository pictureRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.pictureRepository = pictureRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public PetCardListResponse listPetsByUser(Long userId, int pageNumber, int pageSize, String orderBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Pet> page = petRepository.findByUserId(userId, pageable);
        List<Pet> pets = page.getContent();
        List<PetCardResponse> content = pets.stream()
                .map(pet -> PetCardMapper.mapToDto(pet, userId)).collect(Collectors.toList());

        return PetCardListResponse.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .content(content)
                .build();
    }

    @Override
    public PetCardListResponse listFilteredPets(AnimalType animalType, Gender gender, Integer startAge, Integer endAge, String race, String location, int pageNumber, int pageSize, String orderBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Pet> page = petRepository.findAllByFilter(animalType, gender, startAge, endAge, race, location, pageable);

        List<Pet> pets = page.getContent();
        List<PetCardResponse> content = pets.stream()
                .map(pet -> PetCardMapper.mapToDto(pet, pet.getUser().getId())).collect(Collectors.toList());
        return PetCardListResponse.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .content(content)
                .build();
    }

    @Override
    public PetProfileResponse createPet(Long userId, PetProfileRequest petProfileRequest) {
        System.out.println("Searching user");
        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        System.out.println("User searched: " + user);
        Location location = locationRepository.findByName(petProfileRequest.getLocation()).orElse(null);
        if (location == null) {
            location = locationRepository.save(Location.builder()
                    .name(petProfileRequest.getLocation())
                    .build());
        }
        Pet pet = PetProfileMapper.mapToEntity(petProfileRequest, user, location);
        pet.setCreatedDate(new Date());
        pet.setUpdatedDate(new Date());
        Pet petInserted = petRepository.save(pet);

        return PetProfileMapper.mapToDto(petInserted);
    }

    @Override
    public PetProfileResponse getPetById(Long petId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        return PetProfileMapper.mapToDto(pet);
    }

    @Override
    public PetProfileResponse updatePet(Long petId, PetProfileRequest petProfileRequest) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        pet = PetProfileMapper.mapToEntity(petProfileRequest, pet.getUser(), pet.getLocation());
        pet.setUpdatedDate(new Date());
        Pet petUpdated = petRepository.save(pet);
        petUpdated.setUpdatedDate(new Date());
        return PetProfileMapper.mapToDto(petUpdated);
    }

    @Override
    public void deletePet(Long petId) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        List<Picture> pictureList = pet.getPictures();
        pictureList.stream()
                .forEach(pictureRepository::delete);

        petRepository.delete(pet);
    }


}
