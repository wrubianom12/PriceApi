package com.zara.priceapi.domain.port.in;


import com.zara.priceapi.domain.model.PriceCalculateCommand;
import com.zara.priceapi.domain.model.PriceCalculationDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface PriceCalculateHandler {
    PriceCalculationDto handle(PriceCalculateCommand command);

}
