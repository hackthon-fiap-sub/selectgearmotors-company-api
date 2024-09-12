package br.com.selectgearmotors.company.core.ports.out;

import br.com.selectgearmotors.company.core.domain.CompanyType;

import java.util.List;

public interface CompanyTypeRepositoryPort {
    CompanyType save(CompanyType companyType);
    boolean remove(Long id);
    CompanyType findById(Long id);
    List<CompanyType> findAll();
    CompanyType update(Long id, CompanyType companyType);
}
