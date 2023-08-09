package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

         CommentRequest  newCommentRequest = commentRequest.builder()
                .userIdx(userIdx)
                .reviewIdx(longReviewId)
                .content(commentRequest.getContent())
                .build();

        commentService.createComment(newCommentRequest);

        return ApiUtils.success(true, 200, "댓글 추가에 성공했습니다.", null);

    }
}
