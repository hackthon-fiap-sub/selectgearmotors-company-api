package br.com.selectgearmotors.company.core.ports.in.company;

import br.com.selectgearmotors.company.core.domain.Company;

public interface FindByIdCompanyPort {
    Company findById(Long id);
    Company findByCode(String code);
    Company findByEmail(String email);
}
