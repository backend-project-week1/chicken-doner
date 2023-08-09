package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Comment;
import com.supercoding.chickendoner.entity.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String content;


    public Comment toEntity(Long userIdx, Review review) {
        return Comment.builder()
                .userIdx(userIdx)
                .reviewIdx(review)
                .content(this.content)
                .isDeleted(false).build();

    }

}
