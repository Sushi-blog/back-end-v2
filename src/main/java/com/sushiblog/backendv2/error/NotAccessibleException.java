package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class NotAccessibleException extends SushiException {
    public NotAccessibleException() {
        super(ErrorCode.NOT_ACCESSIBLE);
    }
}
