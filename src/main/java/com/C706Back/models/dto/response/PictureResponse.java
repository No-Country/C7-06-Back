package com.C706Back.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter @Setter
public class PictureResponse {

    private Long id;
    private String path;
}

