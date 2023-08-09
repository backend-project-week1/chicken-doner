package com.supercoding.chickendoner.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    private String phoneNumber;

}
