package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequest extends ReviewRequest{

    private Long chickenId;

    public Review of(ReviewCreateRequest reviewCreateRequest, User user , Chicken chicken){
        return Review.builder()
            .chickenIdx(chicken.getId())
            .title(this.getTitle())
            .content(this.getContent())
            .point(this.getPoint())
            .userIdx(user)
            .isDeleted(false)
            .build();
    }
}
