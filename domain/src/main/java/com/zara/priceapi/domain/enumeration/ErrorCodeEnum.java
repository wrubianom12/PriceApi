package com.zara.priceapi.domain.enumeration;


import lombok.Getter;

@Getter
public enum ErrorCodeEnum {


    UNCONTROLLED_ERROR("Uncontrolled error.", 500),
    ERROR_CALCULATE_PRICE_GROUP("It is not possible to calculate the price.", 200);

    private final Integer httpStatus;
    private final String message;

    ErrorCodeEnum(String message, Integer httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}