package com.sushiblog.backendv2.usecase.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class SignInRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
