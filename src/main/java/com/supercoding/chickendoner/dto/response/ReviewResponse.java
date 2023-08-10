package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.common.util.DateUtils;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import java.text.ParseException;
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
public class ReviewResponse {//보내줄 데이터

    private Long idx;
    private String title;
    private String content;
    private Double point;
    private String createAt;
    private String writer;
    private boolean isDeleted;


    public ReviewResponse getReview(Review review, User user) {//FE 보내줄 데이터
        return ReviewResponse.builder()
            .idx(review.getId())
            .title(review.getTitle())
            .content(review.getContent())
            .point(review.getPoint())
            .isDeleted(false)
            .writer(user.getNickname()) //회원 전체가 아닌 작성자로 알 수 있게
            .createAt(String.valueOf(review.getCreateAt()))
            .build();
    }

    public ReviewResponse getMyReview(Review review) {//FE 보내줄 데이터
        return ReviewResponse.builder()
            .idx(review.getId())
            .title(review.getTitle())
            .content(review.getContent())
            .point(review.getPoint())
            .isDeleted(false)
            .writer(review.getUser().getNickname())
            .createAt(String.valueOf(review.getCreateAt()))
            .build();
    }
}


