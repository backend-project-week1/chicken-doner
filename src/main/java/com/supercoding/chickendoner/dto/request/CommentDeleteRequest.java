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
@Schema(description = "댓글 삭제 DTO")
public class CommentDeleteRequest {

    @Schema(description = "댓글의 번호 필드", defaultValue = "0")
    private Long commentIdx;


}
