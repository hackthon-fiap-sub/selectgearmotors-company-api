package br.com.selectgearmotors.company.core.ports.in.companytype;

import br.com.selectgearmotors.company.core.domain.CompanyType;

public interface UpdateCompanyTypePort {
    CompanyType update(Long id, CompanyType companyType);
}
