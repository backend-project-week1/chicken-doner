package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.LoginRequest;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.dto.request.UserRequest;
import com.supercoding.chickendoner.dto.request.UserUpdateRequest;
import com.supercoding.chickendoner.dto.response.LoginResponse;
import com.supercoding.chickendoner.dto.response.MyScrapResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Objects;
import java.util.List;

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
        if (Boolean.TRUE.equals(loginUser.getIsDeleted())) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        LoginResponse loginResponse = userService.makeLoginResp(loginUser);

        return ApiUtils.success(true, 200, "로그인 성공", loginResponse);
    }

    @Auth
    @DeleteMapping("/delete")
    public CommonResponse<Object> delete() {
        Long userIdx = AuthHolder.getUserIdx();
        String deletedUser = userService.deleteUser(userIdx);
        return ApiUtils.success(true, 200, deletedUser +" 회원 탈퇴 성공", null);
    }

    @Auth
    @GetMapping("/profile")
    public CommonResponse<Object> myProfile() throws ParseException {
        Long userIdx = AuthHolder.getUserIdx();

        UserDetailResponse userDetailResponse = userService.getMyProfile(userIdx);

        return ApiUtils.success(true,200,"조회 성공", userDetailResponse);
    }

    @Auth
    @GetMapping("/scrap")
    public CommonResponse<Object> myScrap() throws ParseException{
        Long userIdx = AuthHolder.getUserIdx();

        List<MyScrapResponse> myScrapResponses= userService.findAllScraps(userIdx);

       return ApiUtils.success(true,200,"내 장바구니 조회 성공", myScrapResponses);

    }


    @Auth
    @PatchMapping("/profile")
    public CommonResponse<Object> patchProfile(@RequestBody UserUpdateRequest userUpdateRequest) {
        Long userIdx = AuthHolder.getUserIdx();
        if (!Objects.equals(userIdx, userUpdateRequest.getUserIdx())) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED);
        }
        userService.patchMyProfile(userUpdateRequest, userIdx);

        return ApiUtils.success(true, 200, userIdx +" 회원 수정 완료", null);
    }


}
