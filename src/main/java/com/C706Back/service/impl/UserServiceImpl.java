package com.C706Back.service.impl;

import com.C706Back.exception.ResourceNotFoundException;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.dto.response.UserProfileResponse;
import com.C706Back.models.entity.Picture;
import com.C706Back.models.entity.User;
import com.C706Back.repository.UserRepository;
import com.C706Back.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileResponse getUserById(Long userId) {
        User user = userRepository
                .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        PictureResponse pictureResponse = null;

        if (user.getPicture() != null)
            pictureResponse = PictureResponse.builder()
                    .id(user.getPicture().getId())
                    .path(user.getPicture().getPath())
                    .build();
        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .description(user.getDescription())
                .phoneNumber(user.getPhoneNumber())
                .pictureResponse(pictureResponse)
                .build();
        return userProfileResponse;
    }
}
