package com.sushiblog.backendv2.usecase.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostsResponse {

    private List<PostResponse> posts;

    private int totalPages;

}
