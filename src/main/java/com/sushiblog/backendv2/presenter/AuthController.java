package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.request.SignInRequest;
import com.sushiblog.backendv2.usecase.dto.response.AccessTokenResponse;
import com.sushiblog.backendv2.usecase.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/sushi/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccessTokenResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

}
