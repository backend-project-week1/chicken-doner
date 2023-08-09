package com.supercoding.chickendoner.dto.response;

import com.supercoding.chickendoner.entity.Scrap;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChickenScrap {

    private Long id;
    private String chickenName;
    private String content;
    private Long price;
    private Long amount;


    public  ChickenScrap toChickenScrap(Scrap scrap){
        return ChickenScrap.builder()
                .chickenName(scrap.getChicken().getChikenName()) // 치킨 엔티티의 이름 필드를 가져와 설정
                .content(scrap.getChicken().getContent())   // 치킨 엔티티의 content 필드를 가져와 설정
                .price(scrap.getChicken().getPrice())       // 치킨 엔티티의 price 필드를 가져와 설정
                .id(scrap.getChicken().getId())
                .amount(scrap.getAmount())
                .build();

    }

}



