package com.angelfg.companiescrud.services;

import com.angelfg.companiescrud.entities.Company;

public interface CompanyService {
    Company create(Company company);
    Company readByName(String name);
    Company update(Company company, String name);
    void delete(String name);
}
