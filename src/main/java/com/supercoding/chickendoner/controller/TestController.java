package com.supercoding.chickendoner.controller;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Data
    private static class TestStatus {
        private int code;
        private String message;
    }

    @Data
    private static class TestOutput {
        private int id;
        private String username;
        private String userProfile;
        private String content;
        private boolean isMine;
    }


    @GetMapping("/api/test")
    public CommonResponse<Object> getTest() {

        TestStatus testStatus = new TestStatus();
        testStatus.setCode(200);
        testStatus.setMessage("Successfully get comments.");

        TestOutput testOutput = new TestOutput();
        testOutput.setId(1);
        testOutput.setUsername("노원호");
        testOutput.setMine(false);
        testOutput.setContent("글내용입니다글내용입니다글내용입니다글내용입니다글내용입니다글내용입니다글내용입니다");
        testOutput.setUserProfile("resource/~~~/~~.jpg");


        return ApiUtils.success(true, 200,  "성공", testOutput);
    }
}
