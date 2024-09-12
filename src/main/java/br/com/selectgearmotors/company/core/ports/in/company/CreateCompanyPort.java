package br.com.selectgearmotors.company.core.ports.in.company;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.domain.Company;

public interface CreateCompanyPort {
    Company save(Company company) throws ResourceFoundException;
}
