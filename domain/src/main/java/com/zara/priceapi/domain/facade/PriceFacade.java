package com.zara.priceapi.domain.facade;


import com.zara.priceapi.domain.model.Price;
import com.zara.priceapi.domain.model.PriceCalculateCommand;
import com.zara.priceapi.domain.model.PriceCalculationDto;
import com.zara.priceapi.domain.model.enumeration.ErrorCodeEnum;
import com.zara.priceapi.domain.model.exception.PriceException;
import com.zara.priceapi.domain.port.in.PriceCalculateHandler;
import com.zara.priceapi.domain.port.out.PricePersistencePort;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class PriceFacade implements PriceCalculateHandler {

    private final PricePersistencePort pricePersistencePort;

    public PriceFacade(PricePersistencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }

    @Override
    public PriceCalculationDto handle(PriceCalculateCommand command) {

        List<Price> prices = pricePersistencePort.findApplicablePrice(command.getDate(), command.getProductId(), command.getBrandId());
        Optional<Price> priceMaxDate = prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));
        if (priceMaxDate.isPresent()) {
            return PriceCalculationDto.builder()
                    .productId(command.getProductId())
                    .brandId(command.getBrandId())
                    .finalPrice(priceMaxDate.get().getPrice())
                    .endDate(priceMaxDate.get().getEndDate())
                    .startDate(priceMaxDate.get().getStartDate())
                    .build();
        } else {
            throw new PriceException(ErrorCodeEnum.ERROR_CALCULATE_PRICE_GROUP);
        }

    }
}
