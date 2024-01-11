package com.zara.priceapi.application;



import com.zara.priceapi.domain.model.Price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UtilPriceTest {

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static List<Price> PRICES_TO_TEST = Arrays.asList(
            Price.builder()
                    .id(1L)
                    .brandId(1L)
                    .startDate(LocalDateTime.parse("2020-06-14 00:00:00", formatter))
                    .endDate(LocalDateTime.parse("2020-12-31 23:59:59", formatter))
                    .priceList(1)
                    .productId(35455L)
                    .priority(0)
                    .price(35.50)
                    .curr("EUR")
                    .build(),
            Price.builder()
                    .id(2L)
                    .brandId(1L)
                    .startDate(LocalDateTime.parse("2020-06-14 15:00:00", formatter))
                    .endDate(LocalDateTime.parse("2020-06-14 18:30:00", formatter))
                    .priceList(2)
                    .productId(35455L)
                    .priority(1)
                    .price(25.45)
                    .curr("EUR")
                    .build(),
            Price.builder()
                    .id(3L)
                    .brandId(1L)
                    .startDate(LocalDateTime.parse("2020-06-15 00:00:00", formatter))
                    .endDate(LocalDateTime.parse("2020-06-15 11:00:00", formatter))
                    .priceList(3)
                    .productId(35455L)
                    .priority(1)
                    .price(30.50)
                    .curr("EUR")
                    .build(),
            Price.builder()
                    .id(4L)
                    .brandId(1L)
                    .startDate(LocalDateTime.parse("2020-06-15 16:00:00", formatter))
                    .endDate(LocalDateTime.parse("2020-12-31 23:59:59", formatter))
                    .priceList(4)
                    .productId(35455L)
                    .priority(1)
                    .price(38.95)
                    .curr("EUR")
                    .build()
    );

}
