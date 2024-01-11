package com.zara.priceapi.application.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorInfoDTO {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;

    public ErrorInfoDTO(Instant timestamp, Integer status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
