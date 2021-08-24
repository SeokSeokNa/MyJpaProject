package com.firstjpa.minijpa.api;

import lombok.Getter;

@Getter
public enum StatusEnum {
    OK(1,"OK"),
    BAD_REQUEST(2, "BAD_REQUEST"),
    NOT_FOUND(3, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(4, "INTERNAL_SERVER_ERROR"),

    Unsupport(5,"예상하는 형식과 다른 형식이거나 구성 입니다."),
    MalformedJwt(6,"올바른 구성이 아닙니다."),
    Expired(7,"로그인 유효기간이 초과되었습니다. 다시 로그인 해주세요!"),
    Signature(8,"토큰이 위조된 이력이 있습니다!"),
    IllegalArgument(9,"다시 로그인 해주세요")
    ;

    int statusCode;
    String message;

    StatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
