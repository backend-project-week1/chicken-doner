package com.supercoding.chickendoner.common.Error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  "Invalid Input Value"),
    ENTITY_NOT_FOUND(400,  " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),

    // 유저
    HANDLE_ACCESS_DENIED(403, "로그인이 필요합니다."),
    INVALID_INPUT_USERNAME(400, "닉네임을 3자 이상 입력하세요"),
    NOTEQUAL_INPUT_PASSWORD(400,  "비밀번호가 일치하지 않습니다"),
    INVALID_PASSWORD(400,  "비밀번호를 4자 이상 입력하세요"),
    INVALID_USERNAME(400,  "알파벳 대소문자와 숫자로만 입력하세요"),
    NOT_AUTHORIZED(403, "작성자만 수정 및 삭제를 할 수 있습니다."),
    USERNAME_DUPLICATION(400, "이미 등록된 아이디입니다."),
    LOGIN_INPUT_INVALID(400, "로그인 정보를 다시 확인해주세요."),
    NOTFOUND_USER(404,  "해당 이름의 유저가 존재하지 않습니다."),

    // 게시글
    NOTFOUND_POST(404, "해당 게시글이 존재하지 않습니다."),
    CONVERTING_FAILED(400, "파일 변환에 실패했습니다."),

















    // 치킨
    NOTFOUND_ALL_CHICKEN(404,"현재 등록된 치킨이 없습니다."),
    NOTFOUND_CHICKEN(404,"해당 치킨은 존재하지 않습니다.")
            ;
    private final String message;
    private final int status;

    ErrorCode(final int status, final  String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public int getStatus() {
        return status;
    }
}
