package br.com.selectgearmotors.company.core.service;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.ports.in.company.*;
import br.com.selectgearmotors.company.core.ports.out.CompanyRepositoryPort;
import br.com.selectgearmotors.company.core.domain.Company;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService implements CreateCompanyPort, UpdateCompanyPort, FindByIdCompanyPort, FindCompanysPort, DeleteCompanyPort {

    private final CompanyRepositoryPort companyRepository;

    @Override
    public Company save(Company company) throws ResourceFoundException {
        Company byEmail = findByEmail(company.getEmail());
        if (byEmail != null) {
            throw new ResourceFoundException("Company already exists");
        }
        company.setCode(UUID.randomUUID().toString());
        return companyRepository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        Company resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, company);

            return companyRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    @Override
    public Company findByCode(String code) {
        return companyRepository.findByCode(code);
    }

    @Override
    public List<Company> findAll() {
       return companyRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Company companyById = findById(id);
            if (companyById == null) {
                throw new ResourceFoundException("Company not found");
            }

            companyRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
