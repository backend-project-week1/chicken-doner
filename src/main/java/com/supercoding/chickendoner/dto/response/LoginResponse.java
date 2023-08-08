package com.supercoding.chickendoner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private Long userIdx;
    private String username;
    private String accessToken;
}
