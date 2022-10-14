package com.C706Back.service;

import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Picture;

public interface PictureService {

    Picture getPicture(Long pictureId);
    PictureResponse createPetPicture(Long petId, String path, String key);

    PictureResponse createAvatar(Long userId, String path, String key);

    PictureResponse updatePicture(Long pictureId, String path, String key);

    void deletePicture(Long pictureId);
}
