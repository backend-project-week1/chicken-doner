package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.LoginRequest;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.dto.response.LoginResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.security.TokenProvider;
import com.supercoding.chickendoner.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public CommonResponse<Object> signUp(@RequestBody UserDetailRequest userDetailRequest) {

        if (!userService.checkLoginIdDuplicate(userDetailRequest.getUsername())) {
            String signUpUser = userService.signUp(userDetailRequest);
            return ApiUtils.success(true, 200, signUpUser + " 유저 가입 성공", null);
        } else {
            throw new CustomException(ErrorCode.INVALID_SIGNUP_FILED);
        }
    }

    @PostMapping("/login")
    public CommonResponse<Object> login(@RequestBody LoginRequest loginRequest) {
        User loginUser = userService.login(loginRequest);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        String jwtToken = TokenProvider.createToken(loginUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setNickname(loginUser.getNickname());
        loginResponse.setAccessToken(jwtToken);

        return ApiUtils.success(true, 200, "로그인 성공", loginResponse);

    }

    @GetMapping("/profile")
    public CommonResponse<Object> myProfile (@Parameter(in = ParameterIn.HEADER, hidden = true) Authentication authentication) throws ParseException {
        UserDetailResponse userDetailResponse = userService.getMyProfile(authentication.getName());
        return ApiUtils.success(true,200,"조회 성공", userDetailResponse);
    }

}
