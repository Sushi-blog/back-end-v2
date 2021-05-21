package com.sushiblog.backendv2.usecase.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Categories {

    private Long id;

    private String name;

}
