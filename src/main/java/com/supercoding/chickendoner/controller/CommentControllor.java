package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController(value = "/api/v1/")
public class CommentControllor {

    private CommentService commentService;

    @ApiOperation(value = "리뷰 대댓글 입력", nickname = "리뷰의 대한 대댓을 입력")
    @PostMapping(value = "{review_id}/comment")
    public CommonResponse<Object> createComment(
            @PathVariable("review_id") String reviewId,
            @RequestBody CommentRequest commentRequest
    ) {

        // PathVariable의 String 형태를 Long으로 형변환
        Long longReviewId = Long.valueOf(reviewId);

        commentService.createComment(longReviewId, commentRequest);

        return null;

    }
}
