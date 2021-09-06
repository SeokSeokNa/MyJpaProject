package com.firstjpa.minijpa.api_dto;

import lombok.Getter;

@Getter
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String userName;
    private Long expireDate;

    public TokenResponseDto(String accessToken,String refreshToken, String tokenType , String userName , Long expireDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.userName = userName;
        this.expireDate  = expireDate;
    }
}
