package com.angelfg.companiescrud.services;

import com.angelfg.companiescrud.entities.Category;
import com.angelfg.companiescrud.entities.Company;
import com.angelfg.companiescrud.repositories.CompanyRepository;
import io.micrometer.tracing.Tracer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final Tracer tracer; // trazabilidad
    // Zipkin -> http://localhost:9411/
    // Grafana -> http://localhost:3000/  (admin, admin)
    // Promethues -> http://localhost:9090/

    @Override
    public Company create(Company company) {
        company.getWebSites().forEach(webSite -> {
            if (Objects.isNull(webSite.getCategory())) {
                webSite.setCategory(Category.NONE);
            }
        });

        return this.companyRepository.save(company);
    }

    @Override
    public Company readByName(String name) {

        // Trazabilidad personalizada
        var spam = tracer.nextSpan().name("readByName");
        try (Tracer.SpanInScope spanInScope = this.tracer.withSpan(spam)) {
            log.info("Getting company from DB");
        } finally {
            spam.end();
        }

        return this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        Company companyToUpdate = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));

        companyToUpdate.setLogo(company.getLogo());
        companyToUpdate.setFoundationDate(company.getFoundationDate());
        companyToUpdate.setFounder(company.getFounder());

        return this.companyRepository.save(companyToUpdate);
    }

    @Override
    public void delete(String name) {
        Company companyToDelete = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));

        this.companyRepository.delete(companyToDelete);
    }

}
