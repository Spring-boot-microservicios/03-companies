package com.angelfg.companiescrud.listeners;

import com.angelfg.companiescrud.entities.Company;
import com.angelfg.companiescrud.services.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class CompanyCircuitBreakerListener {

    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    // Viene de report-ms
    @KafkaListener(topics = "consumerCircuitBreakerReport", groupId = "circuitbreaker")
    public void insertMessageEvent(String companyEvent) throws JsonProcessingException {
        log.info("Received event circuitbreaker {}", companyEvent);

        Company company = this.objectMapper.readValue(companyEvent, Company.class);

        this.companyService.create(company); // garantizando que vamos a crear una company
    }

}
