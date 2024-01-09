package com.zara.priceapi.application.service;

import com.zara.priceapi.application.port.PricePersistencePort;
import com.zara.priceapi.application.usescases.IPriceService;
import com.zara.priceapi.domain.Price;
import com.zara.priceapi.domain.PriceCalculationDto;
import com.zara.priceapi.domain.enumeration.ErrorCodeEnum;
import com.zara.priceapi.domain.exception.PriceException;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class PriceService implements IPriceService {

    private final PricePersistencePort pricePersistencePort;

    public PriceService(PricePersistencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }


    @SneakyThrows(PriceException.class)
    public PriceCalculationDto findApplicablePriceByApplicationDateAndProfucIdAndBranId(LocalDateTime applicationDate, Long productId, Long brandId) {
        try {
            List<Price> prices = pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId);
            Optional<Price> priceMaxDate = prices.stream()
                    .max(Comparator.comparingInt(Price::getPriority));
            if (priceMaxDate.isPresent()) {
                return PriceCalculationDto.builder()
                        .productId(productId)
                        .brandId(brandId)
                        .finalPrice(priceMaxDate.get().getPrice())
                        .endDate(priceMaxDate.get().getEndDate())
                        .startDate(priceMaxDate.get().getStartDate())
                        .build();
            } else {
                throw new PriceException(ErrorCodeEnum.ERROR_CALCULATE_PRICE_GROUP);
            }
        }catch (Exception e ){
            throw new PriceException(ErrorCodeEnum.UNCONTROLLED_ERROR);
        }
    }
}
