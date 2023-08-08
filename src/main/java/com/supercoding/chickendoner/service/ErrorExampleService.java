package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ErrorExampleService {

    public void simulateError1() {
        // 여기서 에러를 발생시키는 시뮬레이션을 수행합니다.
        throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
    }
    public void simulateError2() {
        // 여기서 에러를 발생시키는 시뮬레이션을 수행합니다.
        throw new CustomException(ErrorCode.HANDLE_ACCESS_DENIED);
    }
    public void simulateError3() {
        // 여기서 에러를 발생시키는 시뮬레이션을 수행합니다.
        log.error(ErrorCode.USERNAME_DUPLICATION.getMessage());
        throw new CustomException(ErrorCode.USERNAME_DUPLICATION);
    }
}
