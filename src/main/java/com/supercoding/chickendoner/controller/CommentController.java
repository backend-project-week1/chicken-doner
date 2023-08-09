package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.CommentGetRequest;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.dto.response.CommentGetResponse;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @Auth
    @ApiOperation(value = "리뷰 대댓글 입력", nickname = "리뷰의 대한 대댓을 입력")
    @PostMapping(value = "/{review_id}/comment")
    public CommonResponse<Object> createComment(
            @PathVariable("review_id") String reviewId,
            @RequestBody CommentRequest commentRequest
    ) {
        // @Auth를 통해서 로그인된 유저idx 받기
        Long userIdx = AuthHolder.getUserIdx();

        // PathVariable의 String 형태를 Long으로 형변환
        Long longReviewId = Long.valueOf(reviewId);

        // 받아온 유저번호, 리뷰번호, 댓글내용을 받아와 Request에 추가
        CommentRequest newCommentRequest = commentRequest.builder()
                .userIdx(userIdx)
                .reviewIdx(longReviewId)
                .content(commentRequest.getContent())
                .build();

        // Request객체를 서비스로 전송
        commentService.createComment(newCommentRequest);

        // 결과 반환
        return ApiUtils.success(true, 200, "댓글 추가에 성공했습니다.", null);
    }


    @ApiOperation(value = "게시물 별 리뷰 조회")
    @GetMapping(value = "/{review_id}/comment")
    public CommonResponse<Object> getCommentsByReview(
            @PathVariable("review_id") String reviewIdx
    ) {
        // PathVariable의 String 형태를 Long으로 형변환
        Long longReviewIdx = Long.valueOf(reviewIdx);

        // 댓글GetRequest 선언
        CommentGetRequest request = new CommentGetRequest();

        // 객체 속 선언한 빌더 실행 후 결과 담기
        CommentGetRequest commentsGetByReview = request.getCommentsGetByReview(longReviewIdx);

        List<CommentGetResponse> commentByReview = commentService.getCommentByReview(commentsGetByReview);

        return ApiUtils.success(true, 200, "댓글 리스트 출력", commentByReview);
    }


}
