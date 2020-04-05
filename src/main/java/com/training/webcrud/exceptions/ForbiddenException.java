package com.training.webcrud.exceptions;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private String messageKey;

    public ForbiddenException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }
}
