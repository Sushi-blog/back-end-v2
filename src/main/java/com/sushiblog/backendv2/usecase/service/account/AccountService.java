package com.sushiblog.backendv2.usecase.service.account;

import com.sushiblog.backendv2.usecase.dto.request.SignUpRequest;

public interface AccountService {
    void signUp(SignUpRequest signUpRequest);
    void updateNickname(String nickname);
    void deleteUser();
}
