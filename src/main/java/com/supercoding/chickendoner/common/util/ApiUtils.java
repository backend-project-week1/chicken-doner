package com.supercoding.chickendoner.common.util;

import com.supercoding.chickendoner.common.CommonResponse;
import lombok.Data;

public class ApiUtils {

    public static <T> CommonResponse<T> success(boolean result, int code, String message, T data) {

        @Data
        class Status {
            private int code;
            private String message;
        }

        Status status = new Status();
        status.setCode(code);
        status.setMessage(message);

        return new CommonResponse<>(result, (T) status, data);
    }
}
