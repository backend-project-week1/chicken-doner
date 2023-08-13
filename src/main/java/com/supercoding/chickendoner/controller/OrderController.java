package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import com.supercoding.chickendoner.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @Auth
    @PostMapping("/")
    public CommonResponse<Object> doOrder() {
       Long userIdx = AuthHolder.getUserIdx();
        orderService.doOrder(userIdx);
        return ApiUtils.success(true, 200, "주문 성공", null);
    }

}
