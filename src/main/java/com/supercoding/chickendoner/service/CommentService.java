package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.CommentGetRequest;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.dto.request.CommentUpdateRequest;
import com.supercoding.chickendoner.dto.response.CommentGetResponse;
import com.supercoding.chickendoner.entity.Comment;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.CommentRepository;
import com.supercoding.chickendoner.repository.ReviewRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    /*댓글 작성*/
    @Transactional
    public void createComment(Long userIdx, Long reviewId, CommentRequest commentRequest) {

        // 리뷰객체 불러오면서 예외처리
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_REVIEW));
        // 유저객체 불러오면서 예외처리
        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        // 입력된 리뷰아이디를 조회하여 실제 등록되어있는 리뷰인지 확인한다 (값이 있으면 true)
        boolean isExistReview = reviewRepository.existsByIdAndIsDeletedFalse(reviewId);


        // 리뷰번호, 댓글내용 예외처리
        if (!isExistReview) {
            throw new CustomException(ErrorCode.NOTFOUND_REVIEW);
        } else if (commentRequest.getContent().isEmpty()) {
            throw new CustomException(ErrorCode.NOTFOUND_CONTENT);
        }

        Comment comment = commentRequest.toEntity(user, review);

        // 매핑된 엔티티 저장
        commentRepository.save(comment);
    }

    public List<CommentGetResponse> getCommentByReview(CommentGetRequest getRequest) {

        log.info("getRequest" + getRequest);
        // 리뷰에 대한 댓글이 있는지 확인하는 코드
        boolean isExistReview = commentRepository.existsByIdAndIsDeletedFalse(getRequest.getReviewIdx());


        // 해당 리뷰가 존재하는지 확인
        if (!isExistReview) {
            throw new CustomException(ErrorCode.NOTFOUND_REVIEW);
        }

        // 입력된 리뷰아이디를 가진 댓글을 모두 찾아온다
        List<Comment> commentList = commentRepository.findByReviewIdxAndIsDeletedFalse(getRequest.getReviewIdx());
        // 객체를 담을 리스트 생성
        List<CommentGetResponse> getResponse = new ArrayList<>();

        // 반복문을 통해 엔티티객체를 response로 매핑
        for (Comment comment : commentList) {
            // 리스폰스 객체 생성
            CommentGetResponse commentGetResponse = new CommentGetResponse();
            //객체 매핑
            commentGetResponse = commentGetResponse.commentGetResponse(comment);
            // 매핑된 객체 리스트에 추가
            getResponse.add(commentGetResponse);
        }
        return getResponse;
    }

    @Transactional
    public void patchComment(CommentUpdateRequest commentUpdateRequest, Long userIdx, Long commentId) {
        // 댓글 번호를 검색하여 기존 댓글 데이터를 불러오기
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_CHICKEN));

        // 유저 객체 데이터 가져오기
        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        // 작성자만 수정이 가능하므로 작성자 여부를 확인하여 같지 않으면 예외처리
        if (user != findComment.getUserIdx()) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED);
        }

        // 수정할 댓글과 기존댓글 데이터를 가져와 엔티티와 매핑하기
        Comment updateEntity = commentUpdateRequest.updateEntity(commentUpdateRequest, findComment);

        commentRepository.save(updateEntity);
        log.info("test");
    }
}