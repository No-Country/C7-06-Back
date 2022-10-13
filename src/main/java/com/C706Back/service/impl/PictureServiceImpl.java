package com.C706Back.service.impl;

import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Pet;
import com.C706Back.models.entity.Picture;
import com.C706Back.models.entity.User;
import com.C706Back.repository.PetRepository;
import com.C706Back.repository.PictureRepository;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, PetRepository petRepository, UserRepository userRepository) {
        this.pictureRepository = pictureRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PictureResponse createPetPicture(Long petId, String path, String key) {
        Pet pet = petRepository
                .findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", petId));
        Picture picture = Picture.builder()
                .path(path)
                .pet(pet)
                .keyNumber(key)
                .build();
        Picture pictureInserted = pictureRepository.save(picture);
        List<Picture> pictures = pet.getPictures();
        pictures.add(pictureInserted);
        pet.setPictures(pictures);
        petRepository.save(pet);

        return new PictureResponse(pictureInserted.getPath());
    }

    @Override
    public PictureResponse createAvatar(Long userId, String path, String key) {
        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Picture picture = Picture.builder()
                .path(path)
                .pet(null)
                .keyNumber(key)
                .build();
        Picture pictureInserted = pictureRepository.save(picture);
        user.setPicture(pictureInserted);
        userRepository.save(user);

        return new PictureResponse(pictureInserted.getPath());
    }

    @Override
    public PictureResponse updatePicture(Long pictureId, String path, String key) {
        Picture picture = pictureRepository
                .findById(pictureId).orElseThrow(() -> new ResourceNotFoundException("User", "id", pictureId));
        picture.setPath(path);
        picture.setKeyNumber(key);
        Picture pictureInserted = pictureRepository.save(picture);
        return new PictureResponse(pictureInserted.getPath());
    }

    @Override
    public void deletePicture(Long pictureId) {
        Picture picture = pictureRepository
                .findById(pictureId).orElseThrow(() -> new ResourceNotFoundException("User", "id", pictureId));

        pictureRepository.delete(picture);
    }


}
