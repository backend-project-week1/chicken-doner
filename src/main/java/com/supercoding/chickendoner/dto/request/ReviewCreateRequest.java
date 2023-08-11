package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "리뷰 작성 DTO")
public class ReviewCreateRequest extends ReviewRequest{

    @Schema(description = "리뷰를 작성할 치킨 아이디 입력 필드")
    private Long chickenId;

    public Review of(ReviewCreateRequest reviewCreateRequest, User user , Chicken chicken){
        return Review.builder()
            .chickenIdx(chicken.getId())
            .title(this.getTitle())
            .content(this.getContent())
            .point(this.getPoint())
            .user(user)
            .isDeleted(false)
            .build();
    }
}
