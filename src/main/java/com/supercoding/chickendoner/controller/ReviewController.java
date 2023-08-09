package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.response.ReviewResponse;
import com.supercoding.chickendoner.entity.Review;

import com.supercoding.chickendoner.dto.request.ReviewRequest;

import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Auth
    @PostMapping("/review")
    public CommonResponse<Object> createReview(@RequestBody ReviewRequest reviewRequest) {
        //토큰 검증
        Long userIdx = AuthHolder.getUserIdx();
        reviewService.createReview(userIdx, reviewRequest);

            return ApiUtils.success(true, 200, "리뷰 작성  성공",null);
    }
 //게시글 삭제
    @Auth
    @DeleteMapping("/review{ReviewId}")
    public CommonResponse<Object> deleteReview(@PathVariable Long Id) {

        Long userIdx = AuthHolder.getUserIdx();

        reviewService.deleteReview(userIdx,Id);
            return ApiUtils.success(true, 200, "리뷰 삭제 성공", null);
        }
    @Auth
    @PatchMapping("/review{ReviewId}")
    public CommonResponse<Object> updateReview(@PathVariable Long Id,
        @RequestBody ReviewRequest reviewRequest) {

        //유저검증
        Long userIdx = AuthHolder.getUserIdx();
        reviewService.updateReview(userIdx,Id);
        return ApiUtils.success(true, 200, "리뷰 수정 성공", null);
    }

        //상세리뷰 조회
        @GetMapping("/{reviewId}")
            public CommonResponse<Object> reviewDetail(@PathVariable Long Id){//리뷰 Id로 조회
            Review review = reviewService.getReview(Id);
            return ApiUtils.success(true, 200, "상세리뷰 가져오기 성공", review);
        }

        //리뷰 리스트 조회
        @GetMapping("/reviewId")
            public CommonResponse<Object> reviewList(@RequestBody String Type){
        List<ReviewResponse> responses = reviewService.getReviewList(Type);

        if (responses != null){
            return ApiUtils.success(true,200,"리뷰목록 가져오기 성공",responses);
        }else {
            return ApiUtils.success(false,400,"리뷰목록 가져오기 실패",null);
            }
        }
    }


