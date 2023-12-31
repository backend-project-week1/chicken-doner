package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.LikeRequest;
import com.supercoding.chickendoner.dto.request.ReviewCreateRequest;
import com.supercoding.chickendoner.dto.response.ReviewResponse;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.ChickenRepository;
import com.supercoding.chickendoner.repository.LikeRepository;
import com.supercoding.chickendoner.repository.ReviewRepository;
import com.supercoding.chickendoner.repository.UserRepository;

import java.text.ParseException;
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

    private final ChickenRepository chickenRepository;
    private final LikeRepository likeRepository;

    //리뷰 생성
    public void createReview(Long userIdx, ReviewCreateRequest reviewRequest) {

        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new CustomException(ErrorCode.HANDLE_ACCESS_DENIED));

        Chicken chicken = chickenRepository.findById(reviewRequest.getChickenId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_CHICKEN));

        Review review = reviewRequest.of(reviewRequest, user, chicken);
        reviewRepository.save(review);
    }

    //리뷰 가져오기
    public ReviewResponse getReview(Long id) {
        try {
            Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));
            User user = userRepository.findById(review.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

            Long likeCount = likeRepository.countByIsDeletedFalseAndReview_Id(review.getId());

            ReviewResponse reviewResponse = new ReviewResponse();
            reviewResponse = reviewResponse.getReview(review, user, likeCount);

            return reviewResponse;

        } catch (ParseException e) {
            throw new CustomException(ErrorCode.CONVERTING_FAILED);
        }//id로 리뷰를 가져오기
    }

    //리뷰삭제
    public Long deleteReview(Long userIdx, Long id) {
        //게시글 검증
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));
        //유저 검증
        if (!review.getUser().getId().equals(userIdx)) {
            throw new CustomException(ErrorCode.CANT_ACCESS);
        }
        reviewRepository.deleteById(id);
        return review.getId();
    }

    //리뷰 수정
    public void updateReview(Long userIdx, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));

        if (!review.getUser().getId().equals(userIdx)) {
            throw new CustomException(ErrorCode.CANT_ACCESS);
        }
        review.updateContent(review.getContent());
        reviewRepository.save(review);
    }

    //리뷰 리스트
    public List<ReviewResponse> getReviewList(String type) {
        List<Review> reviewList = reviewRepository.findByIsDeletedEquals(false, Sort.by("createAt"));

        ReviewResponse reviewResponse = new ReviewResponse();

        return reviewList.stream()
                .map(review -> {
                    Optional<User> user = userRepository.findById(review.getUser().getId());
                    Long likeCount = likeRepository.countByIsDeletedFalseAndReview_Id(review.getId());
                    try {
                        return reviewResponse.getReview(review, user.get(), likeCount);
                    } catch (ParseException e) {
                        throw new CustomException(ErrorCode.CONVERTING_FAILED);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getListByChicken(Long chickenIdx) {

        if(!chickenRepository.existsById(chickenIdx)) {
            throw new CustomException(ErrorCode.NOTFOUND_CHICKEN);
        }

        List<Review> reviewList = reviewRepository.findAllByIsDeletedFalseAndChickenIdx(chickenIdx, Sort.by("createAt").descending());

        ReviewResponse reviewResponse = new ReviewResponse();
        return reviewList.stream()
                .map(review -> {
                    Long likeCount = likeRepository.countByIsDeletedFalseAndReview_Id(review.getId());
                    try {
                        return reviewResponse.getMyReview(review, likeCount);
                    } catch (ParseException e) {
                        throw new CustomException(ErrorCode.CONVERTING_FAILED);
                    }
                })
                .collect(Collectors.toList());
    }
}
