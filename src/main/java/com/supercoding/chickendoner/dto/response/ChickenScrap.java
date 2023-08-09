package com.supercoding.chickendoner.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChickenScrap {

    private String chickenName;
    private String content;
    private Long price;

}



