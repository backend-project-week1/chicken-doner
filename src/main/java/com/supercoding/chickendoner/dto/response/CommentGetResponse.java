package com.supercoding.chickendoner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CommentGetResponse {

    private Long commentIdx;
    private String writer;
    private String content;
    private String createdAt;

}
