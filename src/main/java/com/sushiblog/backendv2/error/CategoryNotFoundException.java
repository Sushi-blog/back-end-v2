package com.sushiblog.backendv2.error;

import com.sushiblog.backendv2.error.handler.ErrorCode;
import com.sushiblog.backendv2.error.handler.SushiException;

public class CategoryNotFoundException extends SushiException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
