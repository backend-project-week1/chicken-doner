package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.LikeRequest;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @Auth
    @PostMapping("/")
    public CommonResponse<Object> doLike(@RequestBody LikeRequest likeRequest) {
        Long userIdx = AuthHolder.getUserIdx();
        likeService.doLike(userIdx, likeRequest);
        return ApiUtils.success(true, 200, "좋아요 성공 및 취소", null);
    }

}
