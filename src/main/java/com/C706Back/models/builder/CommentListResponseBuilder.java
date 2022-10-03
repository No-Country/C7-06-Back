package com.C706Back.models.builder;

import com.C706Back.models.dto.response.CommentResponse;
import com.C706Back.models.dto.response.CommentListResponse;

import java.util.List;

public class CommentListResponseBuilder implements IBuilder<CommentListResponse> {

    private List<CommentResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;

    public CommentListResponseBuilder() {
    }

    public CommentListResponseBuilder content(List<CommentResponse> content) {
        this.content = content;
        return this;
    }

    public CommentListResponseBuilder pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public CommentListResponseBuilder pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public CommentListResponseBuilder totalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public CommentListResponseBuilder totalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public CommentListResponseBuilder isLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
        return this;
    }

    @Override
    public CommentListResponse build() {
        CommentListResponse commentsResponse = new CommentListResponse();
        commentsResponse.setContent(content);
        commentsResponse.setPageNumber(pageNumber);
        commentsResponse.setPageSize(pageSize);
        commentsResponse.setTotalElements(totalElements);
        commentsResponse.setTotalPages(totalPages);
        commentsResponse.setLastPage(isLastPage);
        return commentsResponse;
    }


}
