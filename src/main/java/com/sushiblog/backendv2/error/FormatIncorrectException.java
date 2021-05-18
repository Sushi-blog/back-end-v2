package com.sushiblog.backendv2.error;


import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class FormatIncorrectException extends SushiException {
    public FormatIncorrectException() {
        super(ErrorCode.FORMAT_INCORRECT);
    }
}
