package com.C706Back.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String address;
    private String description;
    private String phoneNumber;
    private PictureResponse pictureResponse;
}
