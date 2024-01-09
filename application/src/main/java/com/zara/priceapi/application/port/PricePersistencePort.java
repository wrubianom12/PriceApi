package com.zara.priceapi.application.port;

import com.zara.priceapi.domain.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PricePersistencePort {
    List<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}