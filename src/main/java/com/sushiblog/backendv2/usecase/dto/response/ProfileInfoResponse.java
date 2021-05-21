package com.sushiblog.backendv2.usecase.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileInfoResponse {

    private String email;

    private String nickname;

}
