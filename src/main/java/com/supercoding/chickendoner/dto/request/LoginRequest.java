package com.supercoding.chickendoner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "아이디 입력 필드", defaultValue = "아이디를 입력하세요.")
    private String username;
    @NotBlank(message = "패스워드를 입력해주세요")
    @Schema(description = "패스워드 입력 필드")
    private String password;

}
