package com.zara.priceapi.infraestructure;

import com.zara.priceapi.application.port.PricePersistencePort;
import com.zara.priceapi.application.service.PriceService;
import com.zara.priceapi.application.usescases.IPriceService;
import com.zara.priceapi.domain.Price;
import com.zara.priceapi.domain.PriceCalculationDto;
import com.zara.priceapi.domain.enumeration.ErrorCodeEnum;
import com.zara.priceapi.domain.exception.PriceException;
import com.zara.priceapi.infraestructure.rest.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPriceService iPriceService;

    @Mock
    private PricePersistencePort pricePersistencePort;

    private PriceService priceService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetPrice_thenReturnPriceCalculationDto() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 35.50;

        //generate data to pricePersistence by inputs criteria
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        //create data to service
        priceService = new PriceService(pricePersistencePort);
        PriceCalculationDto priceCalculationDto = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);

        Mockito.when(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId))
                .thenReturn(priceCalculationDto);

        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.finalPrice").value(priorityPriceResult));
    }


    @Test
    public void whenGetPrice_andServiceThrows_thenRespondWithStatus5xxUNCONTROLLED_ERROR() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;

        when(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId))
                .thenThrow(new PriceException(ErrorCodeEnum.UNCONTROLLED_ERROR));

        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void whenGetPrice_andServiceThrows_thenRespondWithStatus3xxERROR_CALCULATE_PRICE_GROUP() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;

        when(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId))
                .thenThrow(new PriceException(ErrorCodeEnum.ERROR_CALCULATE_PRICE_GROUP));

        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void whenGetPrice_thenReturnPriceAndValidateDatastructureDtoStructure() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 35.50;

        //generate data to pricePersistence by inputs criteria
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        //create data to service
        priceService = new PriceService(pricePersistencePort);
        PriceCalculationDto priceCalculationDto = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);

        Mockito.when(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId))
                .thenReturn(priceCalculationDto);

        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.brandId").exists())
                .andExpect(jsonPath("$.finalPrice").exists())
                .andExpect(jsonPath("$.endDate").exists())
                .andExpect(jsonPath("$.startDate").exists());
    }


    @Test
    public void whenGetPrice_thenReturnPriceAndValidateDatastructureNotNullable() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
        double priorityPriceResult = 35.50;

        //generate data to pricePersistence by inputs criteria
        when(pricePersistencePort.findApplicablePrice(applicationDate, productId, brandId)).thenReturn(filterPriceListByApplicationDateAndProductIdAndBranId(applicationDate, brandId, productId));
        //create data to service
        priceService = new PriceService(pricePersistencePort);
        PriceCalculationDto priceCalculationDto = priceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId);

        Mockito.when(iPriceService.findApplicablePriceByApplicationDateAndProfucIdAndBranId(applicationDate, productId, brandId))
                .thenReturn(priceCalculationDto);

        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(notNullValue())))
                .andExpect(jsonPath("$.brandId", is(notNullValue())))
                .andExpect(jsonPath("$.finalPrice", is(priorityPriceResult)))
                .andExpect(jsonPath("$.endDate", is(notNullValue())))
                .andExpect(jsonPath("$.startDate", is(notNullValue())));
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