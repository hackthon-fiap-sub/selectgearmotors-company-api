package br.com.selectgearmotors.company.core.service;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.in.companytype.*;
import br.com.selectgearmotors.company.core.ports.out.CompanyTypeRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyTypeService implements CreateCompanyTypePort, UpdateCompanyTypePort, FindByIdCompanyTypePort, FindCompanyTypesPort, DeleteCompanyTypePort {

    private final CompanyTypeRepositoryPort companyTypeRepository;

    @Autowired
    public CompanyTypeService(CompanyTypeRepositoryPort companyTypeRepository) {
        this.companyTypeRepository = companyTypeRepository;
    }

    @Override
    public CompanyType save(CompanyType companyType) {
        return companyTypeRepository.save(companyType);
    }

    @Override
    public CompanyType update(Long id, CompanyType product) {
        CompanyType resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return companyTypeRepository.save(resultById);
        }

        return null;
    }

    @Override
    public CompanyType findById(Long id) {
        return companyTypeRepository.findById(id);
    }

    @Override
    public List<CompanyType> findAll() {
       return companyTypeRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            CompanyType companyTypeById = findById(id);
            if (companyTypeById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            companyTypeRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
