package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Comment;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "댓글 작성 요청 DTO")
public class CommentRequest {

    @Schema(description = "댓글 작성 필드",  defaultValue = "댓글 내용을 입력하세요.")
    private String content;


    public Comment toEntity(User userIdx, Review review) {
        return Comment.builder()
                .userIdx(userIdx)
                .reviewIdx(review)
                .content(this.content)
                .isDeleted(false).build();

    }

}
