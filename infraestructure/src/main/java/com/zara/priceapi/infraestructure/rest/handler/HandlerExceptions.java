package com.zara.priceapi.infraestructure.rest.handler;

import com.zara.priceapi.domain.exception.PriceException;
import com.zara.priceapi.infraestructure.adapter.dto.ApiResponseDTO;
import com.zara.priceapi.infraestructure.adapter.dto.ErrorInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(PriceException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleExtrajudicialesException(PriceException ex) {
        log.error("Exception: [{}]", ex.getErrorCode().getMessage());
        ApiResponseDTO<Object> apiResponseDTO = new ApiResponseDTO<>(
                null,
                ErrorInfoDTO
                        .builder()
                        .message(ex.getErrorCode().getMessage())
                        .status(ex.getErrorCode().getHttpStatus())
                        .timestamp(Instant.now())
                        .build()
        );
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(apiResponseDTO);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handlerException(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        ApiResponseDTO<Object> apiResponseDTO = new ApiResponseDTO<>(
                null,
                ErrorInfoDTO
                        .builder()
                        .message("Unexpected error occurred. " + ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(Instant.now())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseDTO);
    }
}
