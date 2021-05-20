package com.sushiblog.backendv2.usecase.service.account;

import com.sushiblog.backendv2.usecase.dto.SignUpRequest;

public interface AccountService {
    void signUp(SignUpRequest signUpRequest);
}
