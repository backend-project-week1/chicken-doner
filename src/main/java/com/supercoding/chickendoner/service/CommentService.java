package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.entity.Comment;
import com.supercoding.chickendoner.repository.CommentRepository;
import com.supercoding.chickendoner.repository.주환리뷰임시레파이토리;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    //ReiewRepository 파일이 없어서 임시로 만들었습니다.
    //
    private final 주환리뷰임시레파이토리 reviewRepository;
//    private final ReviewRepository reviewRepository;

    /*댓글 작성*/
    @Transactional
    public void createComment(CommentRequest commentRequest) {

        // 입력된 리뷰아이디를 조회하여 실제 등록되어있는 리뷰인지 확인한다 (값이 있으면 true)
        boolean isExistReview = reviewRepository.existsById(commentRequest.getReviewIdx());


        // 유저번호, 리뷰번호, 댓글내용 예외처리
        if (commentRequest.getUserIdx() == null) {
            throw new CustomException(ErrorCode.NOTFOUND_USER);
        } else if (!isExistReview) {
            throw new CustomException(ErrorCode.NOTFOUND_REVIEW);
        } else if (commentRequest.getContent().isEmpty()) {
            throw new CustomException(ErrorCode.NOTFOUND_CONTENT);
        }


        // 빌더패턴으로 Request를 엔티티로 매핑
        Comment comment = Comment.builder()
                .userIdx(commentRequest.getUserIdx())
                .reviewIdx(commentRequest.getReviewIdx())
                .content(commentRequest.getContent())
                //JPA가 자동으로 만들어주기 때문에 null로 전송
                //createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .isDeleted(false)
                .build();

        // 매핑된 엔티티 저장
        commentRepository.save(comment);
    }
}
