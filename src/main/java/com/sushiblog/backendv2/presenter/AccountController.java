package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.SignUpRequest;
import com.sushiblog.backendv2.usecase.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/sushi/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        accountService.signUp(request);
    }

}
