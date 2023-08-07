package com.supercoding.chickendoner.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final boolean result;
    private final T status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T output;

    @Builder
    public CommonResponse(boolean result, T status, T output) {
        this.result = result;
        this.status = status;
        this.output = output;
    }
}


