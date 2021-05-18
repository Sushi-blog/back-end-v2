package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class UserNotFoundException extends SushiException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
