package br.com.selectgearmotors.company.core.ports.in.company;

import br.com.selectgearmotors.company.core.domain.Company;

import java.util.List;

public interface FindCompanysPort {
    List<Company> findAll();
}
