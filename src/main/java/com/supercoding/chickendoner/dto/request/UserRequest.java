package com.supercoding.chickendoner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Schema(description = "유저 공통 DTO")
public class UserRequest {
    @NotBlank(message = "닉네임을 입력해주세요")
    @Schema(description = "닉네임 필드")
    private String nickname;

    @NotBlank(message = "주소를 입력해주세요")
    @Schema(description = "주소 필드")
    private String address;

    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    @Schema(description = "휴대폰 번호 필드")
    private String phoneNumber;

}
