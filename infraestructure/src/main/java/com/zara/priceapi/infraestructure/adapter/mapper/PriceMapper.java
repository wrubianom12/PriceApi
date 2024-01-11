package com.zara.priceapi.infraestructure.adapter.mapper;

import com.zara.priceapi.domain.model.Price;
import com.zara.priceapi.infraestructure.adapter.entity.PriceEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "brandId", target = "brandId"),
                    @Mapping(source = "startDate", target = "startDate"),
                    @Mapping(source = "endDate", target = "endDate"),
                    @Mapping(source = "priceList", target = "priceList"),
                    @Mapping(source = "productId", target = "productId"),
                    @Mapping(source = "priority", target = "priority"),
                    @Mapping(source = "price", target = "price"),
                    @Mapping(source = "curr", target = "curr")

            }
    )
    Price toPrice(PriceEntity priceEntity);

    List<Price> toPrices(List<PriceEntity> priceEntities);

    @InheritInverseConfiguration
    PriceEntity toPriceEntity(Price price);

}
