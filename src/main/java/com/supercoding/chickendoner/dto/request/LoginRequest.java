package com.supercoding.chickendoner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String username;
    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;

}
