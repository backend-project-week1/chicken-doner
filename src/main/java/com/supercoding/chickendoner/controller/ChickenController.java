package com.supercoding.chickendoner.controller;


import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.response.ChickenResponse;
import com.supercoding.chickendoner.service.ChickenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags= "치킨 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChickenController {

    private final ChickenService chickenService;

    @ApiOperation(value = "치킨 리스트 조회", nickname = "치킨 리스트를 모두 가져옵니다.")
    @GetMapping("/chicken")
    public CommonResponse<Object> getChickenList() {
        // 바인딩된
        List<ChickenResponse> chickenList = chickenService.getChickenList();

        return ApiUtils.success(true, 200, "성공", chickenList);
    }
    @GetMapping("/chicken/{chicken_id}")
    @ApiOperation(value = "치킨 상세 조회")
    public CommonResponse<Object> getChickenOne(@PathVariable("chicken_id") String chickenId) {
        Long longChicken = Long.valueOf(chickenId);

        ChickenResponse chickenOne = chickenService.getChickenOne(longChicken);

        return ApiUtils.success(true, 200, "성공", chickenOne);
    }
}
