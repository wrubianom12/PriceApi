package com.zara.priceapi.infraestructure.adapter;

import com.zara.priceapi.application.port.PricePersistencePort;
import com.zara.priceapi.domain.Price;
import com.zara.priceapi.infraestructure.adapter.mapper.PriceMapper;
import com.zara.priceapi.infraestructure.adapter.repository.PricecrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PricecrudRepository pricecrudRepository;

    private final PriceMapper priceMapper;

    public PricePersistenceAdapter(PricecrudRepository pricecrudRepository, PriceMapper priceMapper) {
        this.pricecrudRepository = pricecrudRepository;
        this.priceMapper = priceMapper;
    }

    public  List<Price>  findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return this.priceMapper
                .toPrices(this.pricecrudRepository.findPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate));
    }

}
