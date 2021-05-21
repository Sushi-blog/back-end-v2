package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class IncorrectNicknameFormatException extends SushiException {
    public IncorrectNicknameFormatException() {
        super(ErrorCode.INVALID_NICKNAME);
    }
}
