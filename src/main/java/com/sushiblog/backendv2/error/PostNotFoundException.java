package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class PostNotFoundException extends SushiException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
