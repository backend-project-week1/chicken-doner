package com.supercoding.chickendoner.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private Long userIdx;
    private Long reviewIdx;
    private String content;


}
