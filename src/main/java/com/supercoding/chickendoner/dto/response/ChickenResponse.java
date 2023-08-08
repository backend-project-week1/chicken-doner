package com.supercoding.chickendoner.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChickenResponse {

    private Long idx;
    private String chickenName;
    private String content;
    private Long price;

}