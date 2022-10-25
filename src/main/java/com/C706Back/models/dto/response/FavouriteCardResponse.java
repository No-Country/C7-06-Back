package com.C706Back.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class FavouriteCardResponse {
    private Long id;
    private Long userId;
    private Long petId;
    private PictureResponse pictureResponse;
    private String name;
    private String race;
    private String gender;
    private int age;

    public FavouriteCardResponse() {
    }
}
