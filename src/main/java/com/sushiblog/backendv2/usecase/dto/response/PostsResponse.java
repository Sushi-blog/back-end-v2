package com.sushiblog.backendv2.usecase.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostsResponse {

    private List<PostResponse> posts;

}
