package com.sushiblog.backendv2.usecase.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailResponse {

    private Long categoryId;

    private String writer;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private String filePath;

}
