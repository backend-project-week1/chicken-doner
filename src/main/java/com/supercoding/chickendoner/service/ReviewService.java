package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.ReviewRequest;
import com.supercoding.chickendoner.dto.response.ReviewResponse;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.ReviewRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    //리뷰 생성
    public void createReview(Long userIdx, ReviewRequest reviewRequest) {
        User user = userRepository.findById(userIdx)
            .orElseThrow(() -> new CustomException(ErrorCode.HANDLE_ACCESS_DENIED));
        Review review = reviewRequest.of(reviewRequest,user);
        reviewRepository.save(review);
    }
    //리뷰 가져오기
    public Review getReview(Long Id) {
        return reviewRepository.findById(Id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));//id로 리뷰를 가져오기
    }

    //리뷰삭제
    public void deleteReview(Long userIdx, Long id) {
        //게시글 검증
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));
    //유저 검증
        if(review.getUserIdx().getId().equals(userIdx)){
            throw new CustomException(ErrorCode.CANT_ACCESS);
        }
        reviewRepository.deleteById(id);
    }
//리뷰 수정
    public void updateReview(Long userIdx, Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));

        if(review.getUserIdx().getId().equals(userIdx)){
            throw new CustomException(ErrorCode.CANT_ACCESS);
        }
        review.updateContent(review.getContent());
        reviewRepository.save(review);
    }
    //좋아요 순
    public List<ReviewResponse> getReviewList(String Type) {
        List<Review> reviewList = reviewRepository.findByIsDeletedEquals(false, Sort.by("createdAt"));
        ReviewResponse reviewResponse = new ReviewResponse();

        return reviewList.stream()
            .map(review -> {
                Optional<User> user = userRepository.findById(review.getUserIdx().getId());
               return reviewResponse.getReview(review, user.get());
            })
            .collect(Collectors.toList());

    }
}
