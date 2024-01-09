package com.zara.priceapi.infraestructure.adapter.repository;

import com.zara.priceapi.infraestructure.adapter.entity.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PricecrudRepository extends CrudRepository<PriceEntity, Long> {
    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND :inputDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findPricesByBrandIdAndProductIdAndDate(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("inputDate") LocalDateTime inputDate
    );


}
