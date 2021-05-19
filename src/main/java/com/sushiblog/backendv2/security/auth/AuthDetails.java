package com.sushiblog.backendv2.security.auth;

import com.sushiblog.backendv2.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor //AuthDetailsService에서 AuthDetails::new하려고
public class AuthDetails implements UserDetails {

    private final User user; //불변객체를 만들기 위해
    // 객체가 개발자의 의도와 상관없이 수정되는걸 막기 위해 정도로 보면 됨

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); //유저에게 부여하는 권한이 없어서 걍 빈 리스트 주는 거임
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    //다 true로 해야 인증이 된다
    @Override
    public boolean isAccountNonExpired() { //계정이 만료되지 않음
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 잠기지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //자격 증명이 만료되지 않음
        return true;
    }

    @Override
    public boolean isEnabled() { //활성화됨
        return true;
    }

}