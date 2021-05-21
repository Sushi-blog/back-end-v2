package com.sushiblog.backendv2.usecase.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private Long categoryId;

}
