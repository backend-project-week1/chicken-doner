package com.supercoding.chickendoner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {

    private Long idx;
    private String title;
    private String content;
    private Double point;
    private Timestamp createAt;
    private String writer;

    //todo 리스트는 여기서 구현

}
