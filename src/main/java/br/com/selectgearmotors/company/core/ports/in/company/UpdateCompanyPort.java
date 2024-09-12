package br.com.selectgearmotors.company.core.ports.in.company;

import br.com.selectgearmotors.company.core.domain.Company;

public interface UpdateCompanyPort {
    Company update(Long id, Company company);
}
