package com.zara.priceapi.infraestructure.rest.controller;


import com.zara.priceapi.application.usescases.IPriceService;
import com.zara.priceapi.domain.PriceCalculationDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

    private IPriceService iPriceService;

    public PriceController(IPriceService iPriceService) {
        this.iPriceService = iPriceService;
    }

    @GetMapping
    @RequestMapping("/brands/{brandId}/products/{productId}/calculate-price")
    public ResponseEntity<PriceCalculationDto> getPrice(@PathVariable("brandId") Long brandId,
                                                        @PathVariable("productId") Long productId,
                                                        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return ResponseEntity.ok(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(date, productId, brandId));
    }

}
