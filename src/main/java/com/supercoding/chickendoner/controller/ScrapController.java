package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.request.ScrapRequest;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.ScrapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@Api(tags= "장바구니 API")
public class ScrapController {

    private final ScrapService scrapService;

    @Auth
    @ApiOperation(value= "장바구니(스크랩) 추가", notes = "장바구니 추가")
    @PostMapping("/scrap")
    public CommonResponse<Object> saveScrap(@RequestBody ScrapRequest scrapRequest){
        //(유저가) 치킨(아이템)을 클릭하면 장바구니(scrap)에 등록.
        //id의 치킨 아이디 지정은 서비스에서.

        //유저 아이디
        Long userIdx = AuthHolder.getUserIdx();

//        log.info("userIdx : {} ", userIdx);

        scrapService.saveScrap(scrapRequest.getId(), userIdx);

       return ApiUtils.success(true, 200, "장바구니 추가 성공", null);
    }



    @Auth
    @ApiOperation(value= "장바구니(스크랩) 비우기", notes = "장바구니 비우기")
    @DeleteMapping("/scrap")
    public CommonResponse<Object> deleteScrap(@RequestBody ScrapRequest scrapRequest){

        //유저 아이디
        Long userIdx = AuthHolder.getUserIdx();

//        log.info("userIdx : {} ", userIdx);

        scrapService.deleteScrap(scrapRequest.getId(), userIdx);

        return ApiUtils.success(true, 200, "장바구니 비우기 성공", null);
    }


}
