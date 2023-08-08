package com.supercoding.chickendoner.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> errors;

    public ErrorResponse(int status, String message) {
        this.code = status;
        this.message = message;
    }
}
