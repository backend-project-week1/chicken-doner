package com.supercoding.chickendoner.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Scrap;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyScrapResponse {

    private ChickenScrap chickenScrap;


    public MyScrapResponse toMyScrapResponse(ChickenScrap chickenScrap){
        return MyScrapResponse.builder()
                .chickenScrap(chickenScrap)
                .build();

    }


}
