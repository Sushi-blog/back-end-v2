package com.sushiblog.backendv2.usecase.service.auth;

import com.sushiblog.backendv2.usecase.dto.request.SignInRequest;
import com.sushiblog.backendv2.usecase.dto.response.AccessTokenResponse;

public interface AuthService {
    AccessTokenResponse signIn(SignInRequest signInRequest);
}
