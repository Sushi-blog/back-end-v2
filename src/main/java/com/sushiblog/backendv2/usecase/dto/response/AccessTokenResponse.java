package com.sushiblog.backendv2.usecase.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {

    @JsonProperty("access-token")
    private String accessToken;

}
