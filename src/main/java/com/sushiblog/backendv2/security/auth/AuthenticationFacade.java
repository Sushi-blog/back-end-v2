package com.sushiblog.backendv2.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade { //인증 객체를 쉽게 가져오려고 만드는 것

    public Authentication getAuthentication() { //토큰 값 반환
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserEmail() { //토큰에 넣은 pk값 찾기
        return this.getAuthentication().getName(); //여기서 name은 PK를 말한다.
    }

}