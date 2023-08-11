package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 수정 DTO")
public class CommentUpdateRequest {
    @Schema(description = "댓글 수정 필드", defaultValue = "댓글 내용을 입력하세요.")
    private String content;


    public Comment updateEntity(CommentUpdateRequest updateRequest, Comment commentInfo) {
        return Comment.builder()
                .id(commentInfo.getId())
                .userIdx(commentInfo.getUserIdx())
                .reviewIdx(commentInfo.getReviewIdx())
                .content(updateRequest.content)
                .isDeleted(false)
                .build();
    }
}
