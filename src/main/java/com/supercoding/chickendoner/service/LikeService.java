package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.LikeRequest;
import com.supercoding.chickendoner.entity.Likes;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.entity.embeddedId.LikeId;
import com.supercoding.chickendoner.repository.LikeRepository;
import com.supercoding.chickendoner.repository.ReviewRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewrepository;

    @Transactional
    public void doLike(Long userIdx, LikeRequest likeRequest) {
        if(userIdx == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        if (likeRequest == null || likeRequest.getReviewIdx() == null) {
            throw new CustomException(ErrorCode.NOTFOUND_REVIEW);
        }
        User user = userRepository.findById(userIdx).orElseThrow(()-> new CustomException(ErrorCode.NOT_AUTHORIZED));
        Review review = reviewrepository.findById(likeRequest.getReviewIdx()).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_REVIEW));

        LikeId likeId = new LikeId(user.getId(), review.getId());

        Optional<Likes> optionalLike = likeRepository.findByLike(review.getId(), user.getId());
        if (optionalLike.isPresent()) {
            likeRepository.updateLikeStatusByUserAndReview(!Boolean.TRUE.equals(optionalLike.get().getIsDeleted()), user.getId(), review.getId());
        } else {
            Likes like = new Likes(likeId, user ,review, false);
            likeRepository.save(like);
        }
    }
}
