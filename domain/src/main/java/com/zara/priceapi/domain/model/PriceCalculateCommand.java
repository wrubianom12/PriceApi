package com.zara.priceapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCalculateCommand {

    private LocalDateTime date;
    private Long productId;
    private Long brandId;

}
