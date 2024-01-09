package com.zara.priceapi.application;

import com.zara.priceapi.application.port.PricePersistencePort;
import com.zara.priceapi.application.service.PriceService;
import com.zara.priceapi.domain.Price;
import com.zara.priceapi.domain.PriceCalculationDto;
import com.zara.priceapi.domain.exception.PriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    @Mock
    private PricePersistencePort pricePersistencePort;
    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenPricesFound_thenReturnHighestPriorityPrice_whenDateIs14thAnd10hrs() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 35.50;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(priorityPriceResult, result.getFinalPrice());
    }

    @Test
    public void whenPricesFound_thenReturnHighestPriorityPrice_whenDateIs14thAnd16hrs() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 16:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 25.45;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(priorityPriceResult, result.getFinalPrice());
    }


    @Test
    public void whenPricesFound_thenReturnHighestPriorityPrice_whenDateIs14thAnd21hrs() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 21:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 35.50;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(priorityPriceResult, result.getFinalPrice());
    }


    @Test
    public void whenPricesFound_thenReturnHighestPriorityPrice_whenDateIs15thAnd10hrs() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 30.50;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(priorityPriceResult, result.getFinalPrice());
    }


    @Test
    public void whenPricesFound_thenReturnHighestPriorityPrice_whenDateIs16thAnd21hrs() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-16 21:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 38.95;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(priorityPriceResult, result.getFinalPrice());
    }

    @Test
    public void whenNoPricesFound_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 1L;
        Long brandId = 1L;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(Collections.emptyList());
        assertThrows(PriceException.class, () -> {
            priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        });
    }


    @Test
    public void whenDateIsOutsidePriceRanges_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-01-01 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        assertThrows(PriceException.class, () -> {
            priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        });
    }

    @Test
    public void whenProductOrBrandIdIsInvalid_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 99999L;  // Un ID de producto que no existe
        Long brandId = 1L;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        assertThrows(PriceException.class, () -> {
            priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        });
    }


    @Test
    public void whenDateIsExactlyOnPriceRangeEdge_thenReturnPrice() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 00:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double expectedPrice = 35.50;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        PriceCalculationDto result = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        assertNotNull(result);
        assertEquals(expectedPrice, result.getFinalPrice());
    }


    @Test
    public void whenDatabaseErrorOccurs_thenThrowPriceException() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenThrow(new RuntimeException("Uncontrolled error."));
        assertThrows(PriceException.class, () -> {
            priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);
        });
    }


    private List<Price> filterPriceListByApplicationDateAndProductIdAndBranId(LocalDateTime applicationDate, Long branid, Long producId) {
        return UtilPriceTest.PRICES_TO_TEST.stream()
                .filter(price -> (!applicationDate.isBefore(price.getStartDate())
                        && !applicationDate.isAfter(price.getEndDate()))
                        && Objects.equals(price.getBrandId(), branid)
                        && Objects.equals(price.getProductId(), producId))
                .toList();
    }
}