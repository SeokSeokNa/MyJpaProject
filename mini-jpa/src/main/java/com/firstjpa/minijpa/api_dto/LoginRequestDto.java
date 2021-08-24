package com.firstjpa.minijpa.api_dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userId;
    private String password;
}
