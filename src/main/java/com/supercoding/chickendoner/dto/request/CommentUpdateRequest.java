package com.supercoding.chickendoner.dto.request;

import com.supercoding.chickendoner.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequest {
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
