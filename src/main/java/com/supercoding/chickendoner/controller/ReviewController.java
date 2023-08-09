package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.ReviewCreateRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Auth
    @PostMapping("/review")
    public CommonResponse<Object> createReview(@RequestBody ReviewCreateRequest reviewRequest) {
        //토큰 검증
        Long userIdx = AuthHolder.getUserIdx();
        reviewService.createReview(userIdx, reviewRequest);

            return ApiUtils.success(true, 200, "리뷰 작성  성공",null);
    }
 //게시글 삭제
    @Auth
    @DeleteMapping("/review/{reviewId}")
    public CommonResponse<Object> deleteReview(@PathVariable("reviewId") Long id) {

        Long userIdx = AuthHolder.getUserIdx();

      Long  deletedReviewId = reviewService.deleteReview(userIdx,id);
            return ApiUtils.success(true, 200, deletedReviewId + " 번 리뷰 삭제 성공", deletedReviewId);
        }
    //게시글 수정
    @Auth
    @PatchMapping("/review/{reviewId}")
    public CommonResponse<Object> updateReview(@PathVariable("reviewId") Long id,
        @RequestBody ReviewRequest reviewRequest) {

        //유저검증
        Long userIdx = AuthHolder.getUserIdx();
        reviewService.updateReview(userIdx,id);
        return ApiUtils.success(true, 200, "리뷰 수정 성공", null);
    }


        //상세리뷰 조회
        @GetMapping("/review/{reviewId}")
            public CommonResponse<Object> reviewDetail(@PathVariable("reviewId")Long id){//리뷰 Id로 조회
            Review review = reviewService.getReview(id);
            return ApiUtils.success(true, 200, "상세리뷰 가져오기 성공", review);
        }

        //리뷰 리스트 조회
        @GetMapping("/review")
            public CommonResponse<Object> reviewList(@RequestParam (required = false) String type){
        List<ReviewResponse> responses = reviewService.getReviewList(type);

        if (responses != null){
            return ApiUtils.success(true,200,"리뷰목록 가져오기 성공",responses);
        }else {
            return ApiUtils.success(false,400,"리뷰목록 가져오기 실패",null);
            }
        }
    }


