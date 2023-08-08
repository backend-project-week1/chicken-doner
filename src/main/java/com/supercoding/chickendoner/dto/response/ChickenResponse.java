package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.entity.Chicken;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class ChickenResponse {

    private Long idx;
    private String chikenName;
    private String content;
    private Long price;


    public ChickenResponse chickenAllList(Chicken chicken) {
        // 치킨의 idx,chicken_name, content, price 리턴
        return ChickenResponse.builder()
                .idx(Long.valueOf(chicken.getId()))
                .chikenName(chicken.getChikenName())
                .content(chicken.getContent())
                .price(chicken.getPrice())
                .build();
    }


}