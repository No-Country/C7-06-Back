package com.C706Back.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter @Setter
public class FavouriteRequest {

    private boolean favourite;

    public  FavouriteRequest(){}
}
