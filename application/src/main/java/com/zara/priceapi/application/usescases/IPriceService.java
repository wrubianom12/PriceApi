package com.zara.priceapi.application.usescases;

import com.zara.priceapi.domain.PriceCalculationDto;

import java.time.LocalDateTime;

public interface IPriceService {

    PriceCalculationDto findApplicablePriceByApplicationDateAndProfucIdAndBranId(LocalDateTime applicationDate, Long productId, Long brandId);
}
