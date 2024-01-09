package com.zara.priceapi.infraestructure;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class PriceApiApplication implements CommandLineRunner {

    @Value("${info.app.name}")
    private String APP_NAME;

    @Value("${info.app.version}")
    private String APP_VERSION;

    public static void main(String[] args) {
        SpringApplication.run(PriceApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Deploy app {} - version {}", APP_NAME, APP_VERSION);
    }
}
