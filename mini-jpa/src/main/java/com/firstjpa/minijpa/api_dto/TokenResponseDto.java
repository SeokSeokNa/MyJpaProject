package com.firstjpa.minijpa.api_dto;

import lombok.Getter;

@Getter
public class TokenResponseDto {
    private String accessToken;
    private String tokenType;
    private String userName;

    public TokenResponseDto(String accessToken, String tokenType , String userName) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.userName = userName;
    }
}
