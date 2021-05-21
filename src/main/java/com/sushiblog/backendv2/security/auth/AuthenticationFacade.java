package com.sushiblog.backendv2.security.auth;

import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.NotAccessibleException;
import com.sushiblog.backendv2.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationFacade { //인증 객체를 쉽게 가져오려고 만드는 것

    private final UserRepository userRepository;

    public Authentication getAuthentication() { //토큰 값 반환
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserEmail() { //토큰에 넣은 pk값 찾기
        return this.getAuthentication().getName(); //여기서 name은 PK를 말한다.
    }

    public boolean isLogin() {
        return getAuthentication() != null;
    }

    public User checkAuth() {
        if(!isLogin()) {
            throw new NotAccessibleException();
        }
        return userRepository.findById(getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);
    }

}