package com.zara.priceapi.infraestructure;


import com.zara.priceapi.domain.facade.PriceFacade;
import com.zara.priceapi.domain.model.enumeration.ErrorCodeEnum;
import com.zara.priceapi.domain.model.exception.PriceException;
import com.zara.priceapi.domain.port.out.PricePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PricePersistencePort pricePersistencePort;

    private PriceFacade priceFacade;


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


        mockMvc.perform(get("/api/v1/brands/{brandId}/products/{productId}/calculate-price", brandId, productId)
                        .param("date", applicationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.finalPrice").value(priorityPriceResult));

    }

    @Test
    public void whenGetPrice_andServiceThrows_thenRespondWithStatus3xxERROR_CALCULATE_PRICE_GROUP() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00", UtilPriceTest.formatter);
        Long productId = 35455L;
        Long brandId = 1L;
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

}