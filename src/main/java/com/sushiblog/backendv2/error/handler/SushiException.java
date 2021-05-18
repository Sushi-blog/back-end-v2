package com.sushiblog.backendv2.error.handler;

import lombok.Getter;

@Getter
public class SushiException extends RuntimeException {

    private final ErrorCode errorCode;

    public SushiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
