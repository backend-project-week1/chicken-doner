package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest extends UserRequest {

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
