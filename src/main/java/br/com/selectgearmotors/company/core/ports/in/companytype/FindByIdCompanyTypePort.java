package br.com.selectgearmotors.company.core.ports.in.companytype;

import br.com.selectgearmotors.company.core.domain.CompanyType;

public interface FindByIdCompanyTypePort {
    CompanyType findById(Long id);
}
