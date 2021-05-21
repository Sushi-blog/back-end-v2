package com.sushiblog.backendv2.usecase.service.account;

import com.sushiblog.backendv2.usecase.dto.request.SignUpRequest;
import com.sushiblog.backendv2.usecase.dto.response.ProfileInfoResponse;

public interface AccountService {
    void signUp(SignUpRequest signUpRequest);
    void updateNickname(String nickname);
    void deleteUser();
    ProfileInfoResponse getProfile(String email);
}
