package com.sushiblog.backendv2.usecase.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;

    private String category;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

}
