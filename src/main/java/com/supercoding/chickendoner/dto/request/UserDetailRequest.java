package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String username;
    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;
    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    private String phoneNumber;

    public User toEntity(String encodePassword) {
        return User.builder()
                .username(this.username)
                .password(encodePassword)
                .nickname(this.nickname)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
