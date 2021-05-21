package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.request.SignUpRequest;
import com.sushiblog.backendv2.usecase.dto.response.ProfileInfoResponse;
import com.sushiblog.backendv2.usecase.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam @NotBlank @Length(max = 10) String nickname) {
        accountService.updateNickname(nickname);
    }

    @DeleteMapping
    public void deleteUser() {
        accountService.deleteUser();
    }

    @GetMapping
    public ProfileInfoResponse getProfile(@RequestParam String email) {
        return accountService.getProfile(email);
    }

}
