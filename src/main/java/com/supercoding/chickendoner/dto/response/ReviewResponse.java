package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.dto.request.ReviewRequest;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long idx;
    private String title;
    private String content;
    private Double point;
    private Timestamp createAt;
    private String writer;
    private boolean isDeleted;

    public ReviewResponse  getReview(Review review,User user){
        return ReviewResponse.builder()
            .title(review.getTitle())
            .content(review.getContent())
            .point(review.getPoint())
            .isDeleted(false)
            .writer(user.getNickname())
            .createAt(review.getCreateAt())
            .build();
    }
}


