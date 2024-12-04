package com.example.paymentservice.exception;


import com.example.paymentservice.constant.GeneralErrorCode;

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