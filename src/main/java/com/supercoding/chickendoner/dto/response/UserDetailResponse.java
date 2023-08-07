package com.supercoding.chickendoner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailResponse {

    private String username;
    private String nickname;
    private String address;
    private String phoneNumber;
    private String createdAt;

}
