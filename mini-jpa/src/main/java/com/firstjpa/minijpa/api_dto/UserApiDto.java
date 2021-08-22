package com.firstjpa.minijpa.api_dto;

import com.firstjpa.minijpa.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserApiDto {
    private String userId;
    private String userName;

    public UserApiDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getName();
    }
}
