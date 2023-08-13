package com.supercoding.chickendoner.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.security.UserRole;
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
@Schema(description = "회원 가입 DTO")
public class UserDetailRequest extends UserRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "아이디 입력 필드")
    private String username;
    @NotBlank(message = "패스워드를 입력해주세요")
    @Schema(description = "패스워드 입력 필드")
    private String password;


    public User toEntity(String encodePassword) {
        return User.builder()
                .username(this.username)
                .password(encodePassword)
                .nickname(this.getNickname())
                .address(this.getAddress())
                .phoneNumber(this.getPhoneNumber())
                .mileage(200000L)
                .isDeleted(false)
                .role(UserRole.USER)
                .build();
    }


}
