package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.common.util.DateUtils;
import com.supercoding.chickendoner.entity.Comment;
import lombok.*;

import java.text.ParseException;


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


    public CommentGetResponse commentGetResponse(Comment comment) throws ParseException {
        return CommentGetResponse.builder()
                .commentIdx(comment.getId())
                .writer(comment.getUserIdx().getNickname())
                .content(comment.getContent())
                .createdAt(DateUtils.convertToString(comment.getCreatedAt()))
//                .createdAt(String.valueOf(comment.getCreatedAt()))
                .build();
    }
}
