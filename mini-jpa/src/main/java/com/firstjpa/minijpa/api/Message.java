package com.firstjpa.minijpa.api;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Message {

    private int status;
    private String message;
    private Object userAccess;

    public Message() {
        this.status = 0;
        this.message = null;
        this.userAccess = null;
    }

    public Message(int status_code, String message) {
        this.status = status_code;
        this.message = message;
    }
}
