package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.common.util.DateUtils;
import com.supercoding.chickendoner.entity.User;
import lombok.*;

import java.text.ParseException;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    private String username;
    private String nickname;
    private String address;
    private String phoneNumber;
    private String createdAt;

    public UserDetailResponse toResponse(User user) throws ParseException {
        return UserDetailResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .createdAt(DateUtils.convertToString(user.getCreateAt()))
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}
