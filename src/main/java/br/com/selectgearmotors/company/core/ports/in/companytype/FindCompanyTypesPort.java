package br.com.selectgearmotors.company.core.ports.in.companytype;

import br.com.selectgearmotors.company.core.domain.CompanyType;

import java.util.List;

public interface FindCompanyTypesPort {
    List<CompanyType> findAll();
}
