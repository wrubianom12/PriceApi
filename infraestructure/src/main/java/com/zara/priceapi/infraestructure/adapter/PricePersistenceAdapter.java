package com.zara.priceapi.infraestructure.adapter;

import com.zara.priceapi.domain.model.Price;
import com.zara.priceapi.domain.port.out.PricePersistencePort;
import com.zara.priceapi.infraestructure.adapter.entity.PriceEntity;
import com.zara.priceapi.infraestructure.adapter.mapper.PriceMapper;
import com.zara.priceapi.infraestructure.adapter.repository.PricecrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PricecrudRepository pricecrudRepository;

    public PricePersistenceAdapter(PricecrudRepository pricecrudRepository) {
        this.pricecrudRepository = pricecrudRepository;
    }

    public List<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return this.pricecrudRepository.findPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate).stream()
                .map(this::convertToPrice)
                .collect(Collectors.toList());

    }

    private Price convertToPrice(PriceEntity priceEntity) {
        return Price.builder()
                .id(priceEntity.getId())
                .brandId(priceEntity.getBrandId())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .priceList(priceEntity.getPriceList())
                .productId(priceEntity.getProductId())
                .priority(priceEntity.getPriority())
                .price(priceEntity.getPrice())
                .curr(priceEntity.getCurr())
                .build();
    }

}
