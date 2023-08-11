package com.supercoding.chickendoner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "좋아요 요청 DTO")
public class LikeRequest {

    @Schema(description = "리뷰 아이디 필드", defaultValue = "0")
    private Long reviewIdx;

}
