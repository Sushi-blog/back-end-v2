package com.sushiblog.backendv2.usecase.service.auth;

import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.UserNotFoundException;
import com.sushiblog.backendv2.security.JwtTokenProvider;
import com.sushiblog.backendv2.usecase.dto.request.SignInRequest;
import com.sushiblog.backendv2.usecase.dto.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccessTokenResponse signIn(SignInRequest signInRequest) {
        return userRepository.findById(signInRequest.getEmail())
                .filter(user -> passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
                .map(User::getEmail)
                .map(u -> {
                    return new AccessTokenResponse(jwtTokenProvider.generateAccessToken(signInRequest.getEmail()));
                })
                .orElseThrow(UserNotFoundException::new);
    }

}
