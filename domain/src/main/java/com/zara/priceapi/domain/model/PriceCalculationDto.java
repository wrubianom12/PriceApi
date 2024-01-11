package com.zara.priceapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculationDto {

    private Long productId;
    private Long brandId;
    private Double finalPrice;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
}
