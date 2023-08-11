package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "유저 수정 DTO")
public class UserUpdateRequest extends UserRequest {

    @Schema(description = "유저 idx 입력 필드")
    private Long userIdx;

    public User updateEntity(UserUpdateRequest userUpdateRequest, User originUser) {
        return User.builder()
                .id(userIdx)
                .password(originUser.getPassword())
                .username(originUser.getUsername())
                .address(userUpdateRequest.getAddress())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .nickname(userUpdateRequest.getNickname())
                .isDeleted(false)
                .build();
    }


}
