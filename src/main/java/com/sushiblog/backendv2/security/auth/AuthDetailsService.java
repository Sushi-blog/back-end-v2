package com.sushiblog.backendv2.security.auth;

import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException { //TokenProvider에서 사용
        //pk를 받아서 유저를 찾고 AuthDetails에 들어가서 정보 확인
        //이 메소드를 구현해야만 나중에 AuthenticationFacade에서 가져올 수 있다.
        return userRepository.findById(userEmail)
                .map(AuthDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }

}