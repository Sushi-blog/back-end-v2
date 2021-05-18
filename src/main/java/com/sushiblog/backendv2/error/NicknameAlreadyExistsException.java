package com.sushiblog.backendv2.error;


import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class NicknameAlreadyExistsException extends SushiException {
    public NicknameAlreadyExistsException() {
        super(ErrorCode.NICKNAME_ALREADY_EXIST);
    }
}
