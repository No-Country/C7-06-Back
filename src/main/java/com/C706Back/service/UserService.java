package com.C706Back.service;

import com.C706Back.models.dto.response.UserProfileResponse;

public interface UserService {

    UserProfileResponse getUserById(Long userId);

}
