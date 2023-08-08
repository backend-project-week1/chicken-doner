package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public CommonResponse<Object> signUp(UserDetailRequest userDetailRequest) {

        if (!userService.checkLoginIdDuplicate(userDetailRequest.getUsername())) {
            String signUpUser = userService.signUp(userDetailRequest);
            return ApiUtils.success(true, 200, signUpUser + " 유저 가입 성공", null);
        } else {
            throw new CustomException(ErrorCode.INVALID_SIGNUP_FILED);
        }

    }

}
