package com.firstjpa.minijpa.controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class UserLoginForm {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    private Boolean isRememberId;
}
