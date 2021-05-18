package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class InvalidTokenException extends SushiException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
