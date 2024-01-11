package com.zara.priceapi.domain.port.out;



import com.zara.priceapi.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PricePersistencePort {
    List<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}