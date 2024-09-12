package br.com.selectgearmotors.company.core.ports.out;

import br.com.selectgearmotors.company.core.domain.Company;

import java.util.List;

public interface CompanyRepositoryPort {
    Company save(Company company);
    boolean remove(Long id);
    Company findById(Long id);
    List<Company> findAll();
    Company update(Long id, Company company);
    Company findByCode(String code);
    Company findByName(String name);
    Company findByEmail(String email);
}
