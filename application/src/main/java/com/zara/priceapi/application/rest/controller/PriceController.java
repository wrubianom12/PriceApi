package com.zara.priceapi.application.rest.controller;


import com.zara.priceapi.domain.model.PriceCalculateCommand;
import com.zara.priceapi.domain.model.PriceCalculationDto;
import com.zara.priceapi.domain.port.in.PriceCalculateHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

    private final PriceCalculateHandler priceCalculateHandler;

    public PriceController(PriceCalculateHandler priceCalculateHandler) {
        this.priceCalculateHandler = priceCalculateHandler;
    }

    @GetMapping
    @RequestMapping("/brands/{brandId}/products/{productId}/calculate-price")
    public ResponseEntity<PriceCalculationDto> getPrice(@PathVariable("brandId") Long brandId,
                                                        @PathVariable("productId") Long productId,
                                                        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return ResponseEntity.ok(priceCalculateHandler.handle(new PriceCalculateCommand(date, productId, brandId)));
    }

}
