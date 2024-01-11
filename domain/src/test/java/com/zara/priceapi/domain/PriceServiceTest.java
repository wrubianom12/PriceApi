package com.zara.priceapi.domain;

import com.zara.priceapi.domain.facade.PriceFacade;
import com.zara.priceapi.domain.model.Price;
import com.zara.priceapi.domain.model.PriceCalculateCommand;
import com.zara.priceapi.domain.model.PriceCalculationDto;
import com.zara.priceapi.domain.model.exception.PriceException;
import com.zara.priceapi.domain.port.out.PricePersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PriceServiceTest {

    @Mock
    private PricePersistencePort pricePersistencePort;
    @InjectMocks
    private PriceFacade priceFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static Stream<Arguments> dataTestPricesAndDates() {
        return Stream.of(
                Arguments.of("2020-06-14 10:00:00", 35.50, "10AM14th"),
                Arguments.of("2020-06-14 16:00:00", 25.45, "4PM14th"),
                Arguments.of("2020-06-14 21:00:00", 35.50, "9PM14th"),
                Arguments.of("2020-06-14 21:00:00", 35.50, "9PM14th"),
                Arguments.of("2020-06-15 10:00:00", 30.50, "10AM15th"),
                Arguments.of("2020-06-16 21:00:00", 38.95, "9PM16th")
        );
    }

    @DisplayName("GroupTestWithDateAndHoursValidation")
    @ParameterizedTest(name = "whenDateIs_{2}_thenReturnPrice_{1}")
    @MethodSource("dataTestPricesAndDates")
    public void whenPricesFound_thenReturnHighestPriorityPrice(String date, double expectedPrice, String nametest) {
        LocalDateTime applicationDate = LocalDateTime.parse(date, UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        Mockito.when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceFacade.handle(new PriceCalculateCommand(applicationDate, productId, brandId));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedPrice, result.getFinalPrice());
    }

    @Test
    public void whenNoPricesFound_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 1L;
        Mockito.when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(Collections.emptyList());
        Assertions.assertThrows(PriceException.class, () -> {
            priceFacade.handle(new PriceCalculateCommand(applicationDate, productId, brandId));
        });
    }


    @Test
    public void whenDateIsOutsidePriceRanges_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-01-01 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        Mockito.when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        Assertions.assertThrows(PriceException.class, () -> {
            priceFacade.handle(new PriceCalculateCommand(applicationDate, productId, brandId));
        });
    }

    @Test
    public void whenProductOrBrandIdIsInvalid_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 99999L;
        Long brandId = 1L;
        Mockito.when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        Assertions.assertThrows(PriceException.class, () -> {
            priceFacade.handle(new PriceCalculateCommand(applicationDate, productId, brandId));
        });
    }


    @Test
    public void whenDateIsExactlyOnPriceRangeEdge_thenReturnPrice() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 00:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double expectedPrice = 35.50;
        Mockito.when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceFacade.handle(new PriceCalculateCommand(applicationDate, productId, brandId));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedPrice, result.getFinalPrice());
    }

    private List<Price> filterPriceListByApplicationDateAndProductIdAndBranId(LocalDateTime applicationDate, Long branid, Long producId) {
        List<Price> result = new ArrayList<>();
        for (Price price : UtilPriceTest.PRICES_TO_TEST) {
            if ((!applicationDate.isBefore(price.getStartDate())
                    && !applicationDate.isAfter(price.getEndDate()))
                    && Objects.equals(price.getBrandId(), branid)
                    && Objects.equals(price.getProductId(), producId)) {
                result.add(price);
            }
        }
        return result;
    }
}