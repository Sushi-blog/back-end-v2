package com.sushiblog.backendv2.error;


import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class EmailAlreadyExistsException extends SushiException {
    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXIST);
    }
}
