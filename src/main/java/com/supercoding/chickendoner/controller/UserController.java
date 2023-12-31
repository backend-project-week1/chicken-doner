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
import com.supercoding.chickendoner.dto.response.ReviewResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags= "유저 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입 API", nickname = "회원가입 API")
    public CommonResponse<Object> signUp(@RequestBody UserDetailRequest userDetailRequest) {

        if (!userService.checkLoginIdDuplicate(userDetailRequest.getUsername())) {
            String signUpUser = userService.signUp(userDetailRequest);
            return ApiUtils.success(true, 200, signUpUser + " 유저 가입 성공", null);
        } else {
            throw new CustomException(ErrorCode.INVALID_SIGNUP_FILED);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인 API", nickname = "로그인 API")
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
    @ApiOperation(value = "유저 탈퇴 API", nickname = "유저 탈퇴 API")
    public CommonResponse<Object> delete() {
        Long userIdx = AuthHolder.getUserIdx();
        String deletedUser = userService.deleteUser(userIdx);
        return ApiUtils.success(true, 200, deletedUser +" 회원 탈퇴 성공", null);
    }

    @Auth
    @GetMapping("/profile")
    @ApiOperation(value = "내 프로필 조회 API", nickname = "내 프로필 조회 API")
    public CommonResponse<Object> myProfile() throws ParseException {
        Long userIdx = AuthHolder.getUserIdx();

        UserDetailResponse userDetailResponse = userService.getMyProfile(userIdx);

        return ApiUtils.success(true,200,"조회 성공", userDetailResponse);
    }

    @Auth
    @GetMapping("/scrap")
    @ApiOperation(value = "내 장바구니 조회 API", nickname = "내 장바구니 조회 API")
    public CommonResponse<Object> myScrap() throws ParseException{
        Long userIdx = AuthHolder.getUserIdx();

        List<MyScrapResponse> myScrapResponses= userService.findAllScraps(userIdx);

       return ApiUtils.success(true,200,"내 장바구니 조회 성공", myScrapResponses);

    }

    @Auth
    @GetMapping("/review")
    @ApiOperation(value = "내가 작성한 리뷰 조회 API", nickname = "내가 작성한 리뷰 조회 API")
    public CommonResponse<Object> myReviews(){

        Long userIdx = AuthHolder.getUserIdx();

        List<ReviewResponse> responses = userService.getMyReviews(userIdx);

        if (responses != null){
            return ApiUtils.success(true,200,"리뷰목록 가져오기 성공",responses);
        }   else {
            return ApiUtils.success(false,400,"리뷰목록 가져오기 실패",null);
        }
    }

    @Auth
    @PatchMapping("/profile")
    @ApiOperation(value = "내 회원 수정 API", nickname = "내 회원 수정 API")
    public CommonResponse<Object> patchProfile(@RequestBody UserUpdateRequest userUpdateRequest) {
        Long userIdx = AuthHolder.getUserIdx();
        if (!Objects.equals(userIdx, userUpdateRequest.getUserIdx())) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED);
        }
        userService.patchMyProfile(userUpdateRequest, userIdx);

        return ApiUtils.success(true, 200, userIdx +" 회원 수정 완료", null);
    }


}
