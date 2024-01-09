package com.zara.priceapi.infraestructure.adapter.dto;

import lombok.Data;

@Data
public class ApiResponseDTO<T> {

    private T data;
    private ErrorInfoDTO errorInfo;

    public ApiResponseDTO(T data, ErrorInfoDTO errorInfo) {
        this.data = data;
        this.errorInfo = errorInfo;
    }

    public ApiResponseDTO(T data) {
        this.data = data;
    }
}
