package com.sushiblog.backendv2.usecase.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateCategoryNameRequest {

    private Long id;

    private String name;

}
