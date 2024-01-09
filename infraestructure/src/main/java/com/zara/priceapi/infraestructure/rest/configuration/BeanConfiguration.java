package com.zara.priceapi.infraestructure.rest.configuration;

import com.zara.priceapi.application.port.PricePersistencePort;
import com.zara.priceapi.application.service.PriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public PriceService priceService(PricePersistencePort pricePersistencePort) {
        return new PriceService(pricePersistencePort);
    }
}
