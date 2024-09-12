package br.com.selectgearmotors.company.core.ports.in.companytype;

import br.com.selectgearmotors.company.core.domain.CompanyType;

public interface CreateCompanyTypePort {
    CompanyType save(CompanyType companyType);
}
