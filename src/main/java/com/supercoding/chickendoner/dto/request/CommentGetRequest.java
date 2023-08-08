package com.supercoding.chickendoner.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentGetRequest {

    private Long reviewIdx;
}
