package com.supercoding.chickendoner.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentGetRequest {
    private Long reviewIdx;

    public CommentGetRequest getCommentsGetByReview(Long reviewIdx) {

        return CommentGetRequest.builder()
                .reviewIdx(reviewIdx)
                .build();
    }
}
