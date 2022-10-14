package com.C706Back.mapper;

import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.entity.Favourite;
import com.C706Back.models.entity.Picture;

public class FavouriteCardMapper {

    public static FavouriteCardResponse mapToDto(Favourite favourite) {
        PictureResponse pictureResponse = null;

        if(favourite.getPet().getPictures() == null || favourite.getPet().getPictures().isEmpty()){
            pictureResponse = null;
        } else {
            Picture picture = favourite.getPet().getPictures().get(0);
            if (picture != null) {

                pictureResponse = PictureResponse.builder()
                        .id(picture.getId())
                        .path(picture.getPath())
                        .build();
            }
        }

        return FavouriteCardResponse.builder()
                .id(favourite.getId())
                .name(favourite.getPet().getName())
                .race(favourite.getPet().getRace())
                .pictureResponse(pictureResponse)
                .age(favourite.getPet().getAge())
                .gender(favourite.getPet().getGender().toString())
                .build();
    }


}
