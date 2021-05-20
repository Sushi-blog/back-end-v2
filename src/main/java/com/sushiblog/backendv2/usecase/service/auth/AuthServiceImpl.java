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
                .map(User::getEmail) //다음 맵으로 email저장해서 넘겨줌.
                .map(u -> new AccessTokenResponse(jwtTokenProvider.generateAccessToken(u))) //map을 쓰면 그 자체로 return하는 것이기 때문에 return을 안써도 됨.
                .orElseThrow(UserNotFoundException::new);
    }

}
