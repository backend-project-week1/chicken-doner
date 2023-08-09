package com.supercoding.chickendoner.dto.request;


import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @NotBlank(message = "별점을 표시해주세요.")
    private double point;


    public Review of(ReviewRequest reviewRequest, User user){
        return Review.builder()
            .title(this.title)
            .content(this.content)
            .point(this.point)
            .userIdx(user)
            .build();
    }

}
