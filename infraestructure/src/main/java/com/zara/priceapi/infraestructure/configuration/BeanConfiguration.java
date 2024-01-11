package com.zara.priceapi.infraestructure.configuration;

import com.zara.priceapi.domain.facade.PriceFacade;
import com.zara.priceapi.domain.port.in.PriceCalculateHandler;
import com.zara.priceapi.domain.port.out.PricePersistencePort;
import com.zara.priceapi.infraestructure.adapter.PricePersistenceAdapter;
import com.zara.priceapi.infraestructure.adapter.repository.PricecrudRepository;
import org.springframework.context.annotation.Bean;


public class BeanConfiguration {

    @Bean
    public PriceCalculateHandler priceCalculateHandler(PricePersistencePort pricePersistencePort) {
        return new PriceFacade(pricePersistencePort);
    }


    @Bean
    public PricePersistencePort pricePersistencePort(PricecrudRepository pricecrudRepository) {
        return new PricePersistenceAdapter(pricecrudRepository);
    }


}
