package com.supercoding.chickendoner.dto.request;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "리뷰 작성 요청 DTO")
public class ReviewRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(description = "제목 작성 필드")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "댓글 삭제 DTO")
    private String content;
    @NotBlank(message = "별점을 표시해주세요.")
    @Schema(description = "댓글 삭제 DTO")
    private double point;
}
