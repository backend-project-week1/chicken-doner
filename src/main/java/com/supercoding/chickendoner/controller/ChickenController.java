package com.supercoding.chickendoner.controller;


import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.response.ChickenResponse;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.service.ChickenService;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController("/api/v1")
public class ChickenController {

    private final ChickenService chickenService;

    @ApiOperation(value = "치킨 리스트 조회", nickname = "치킨 리스트를 모두 가져옵니다.")
    @GetMapping("/chicken")
    public CommonResponse<Object> getChickenList() {
        // 바인딩된
        List<ChickenResponse> chickenList = chickenService.getChickenList();

        return ApiUtils.success(true, 200, "성공",chickenList );
    }


//    @ApiOperation(value = "치킨 상세 조회")
//    public CommonResponse<Object> getChickenOne(){
//
//    }
}
