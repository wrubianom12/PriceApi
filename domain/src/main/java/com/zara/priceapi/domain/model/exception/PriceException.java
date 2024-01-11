package com.zara.priceapi.domain.model.exception;

import com.zara.priceapi.domain.model.enumeration.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class PriceException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public PriceException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public PriceException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public PriceException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
