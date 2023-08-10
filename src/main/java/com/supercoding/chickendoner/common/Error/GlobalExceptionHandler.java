package com.supercoding.chickendoner.common.Error;

import com.supercoding.chickendoner.common.CommonResponse;
import com.supercoding.chickendoner.common.util.ApiUtils;
import com.supercoding.chickendoner.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public CommonResponse<Object> handleException(Exception ex) {
//        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR; // Default to internal server error
//        return ApiUtils.fail(false, errorCode.getStatus(), errorCode.getMessage());
//    }

    @ExceptionHandler(CustomException.class)
    public CommonResponse<Object> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ApiUtils.fail(false, errorCode.getStatus(), errorCode.getMessage());
    }
}
