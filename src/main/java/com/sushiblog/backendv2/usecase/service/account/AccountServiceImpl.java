package com.sushiblog.backendv2.usecase.service.account;

import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.EmailAlreadyExistsException;
import com.sushiblog.backendv2.error.NotAccessibleException;
import com.sushiblog.backendv2.error.UserNotFoundException;
import com.sushiblog.backendv2.security.auth.AuthenticationFacade;
import com.sushiblog.backendv2.usecase.dto.request.SignUpRequest;
import com.sushiblog.backendv2.usecase.dto.response.ProfileInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.findById(signUpRequest.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException();
                });
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .build();
        userRepository.save(user);

        for(int i=1; i<=4; i++) {
            Category category = Category.builder()
                    .user(user)
                    .name("category"+i)
                    .build();
            categoryRepository.save(category);
        }
    }

    @Override
    public void updateNickname(String nickname) {
        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        user.updateNickName(nickname);
        userRepository.save(user);
    }

    @Override
    public void deleteUser() {
        if(!authenticationFacade.isLogin()) {
            throw new NotAccessibleException();
        }

        User user = userRepository.findById(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public ProfileInfoResponse getProfile(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

}
