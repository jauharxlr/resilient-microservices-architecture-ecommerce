package com.example.orderservice.exception;

import com.example.orderservice.constant.GeneralErrorCode;

public class GeneralException extends RuntimeException {
    GeneralErrorCode errorCode;
    public GeneralException(GeneralErrorCode err) {
        super(err.getDescription());
        this.errorCode = err;
    }

    public GeneralErrorCode get() {
        return errorCode;
    }
}