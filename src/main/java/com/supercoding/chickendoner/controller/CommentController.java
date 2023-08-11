package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.CommentDeleteRequest;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.dto.request.CommentUpdateRequest;
import com.supercoding.chickendoner.dto.response.CommentGetResponse;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @Auth
    @ApiOperation(value = "리뷰 대댓글 입력", nickname = "리뷰의 대한 대댓을 입력")
    @PostMapping(value = "/{review_id}/comment")
    public CommonResponse<Object> createComment(
            @PathVariable("review_id") Long reviewId,
            @RequestBody CommentRequest commentRequest
    ) {
        // @Auth를 통해서 로그인된 유저idx 받기
        Long userIdx = AuthHolder.getUserIdx();

        // Request객체를 서비스로 전송
        commentService.createComment(userIdx, reviewId, commentRequest);

        // 결과 반환
        return ApiUtils.success(true, 200, "댓글 추가에 성공했습니다.", null);
    }


    @ApiOperation(value = "리뷰에 대한 댓글 조회")
    @GetMapping(value = "/{review_id}/comment")
    public CommonResponse<Object> getCommentsByReview(
            @PathVariable("review_id") Long reviewIdx
    ) {

        List<CommentGetResponse> commentByReview = commentService.getCommentByReview(reviewIdx);

        return ApiUtils.success(true, 200, "댓글 리스트 출력", commentByReview);
    }

    @Auth
    @ApiOperation(value = "리뷰에 대한 댓글 수정")
    @PatchMapping(value = "/{comment_id}/comment")
    public CommonResponse<Object> patchComment(
            @PathVariable("comment_id") Long commentId,
            @RequestBody CommentUpdateRequest updateRequest
    ) {
        // 로그인된 유저아이디 불러오기
        Long userIdx = AuthHolder.getUserIdx();

        // 수정할 댓글, 유저번호, 댓글번호 매개변수로 전송
        commentService.patchComment(updateRequest, userIdx, commentId);

        return ApiUtils.success(true, 200, userIdx + "댓글 수정이 완료되었습니다.", null);
    }

    @Auth
    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comment")
    public CommonResponse<Object> delete(@RequestBody CommentDeleteRequest commentDeleteRequest) {
        // 로그인된 유저아이디 불러오기
        Long userIdx = AuthHolder.getUserIdx();

        commentService.deleteComment(commentDeleteRequest, userIdx);

        return ApiUtils.success(true, 200, "댓글이 삭제되었습니다.", null);

    }
}
