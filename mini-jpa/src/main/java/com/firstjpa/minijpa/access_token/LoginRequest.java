package com.firstjpa.minijpa.access_token;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String userId;
    private String password;
}
