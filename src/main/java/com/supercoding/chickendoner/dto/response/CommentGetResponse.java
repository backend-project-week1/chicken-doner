package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.entity.Comment;
import lombok.*;


@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class CommentGetResponse {

    private Long commentIdx;
    private String writer;
    private String content;
    private String createdAt;


    public CommentGetResponse commentGetResponse(Comment comment) {
        return CommentGetResponse.builder()
                .commentIdx(comment.getId())
                .writer(String.valueOf(comment.getUserIdx()))
                .content(comment.getContent())
                .createdAt(String.valueOf(comment.getCreatedAt()))
                .build();
    }
}
