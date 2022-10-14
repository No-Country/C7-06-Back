package com.C706Back.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter @Setter
public class FavouriteCardListResponse {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;

    private List<FavouriteCardResponse> content;

    public FavouriteCardListResponse() {
    }
}
