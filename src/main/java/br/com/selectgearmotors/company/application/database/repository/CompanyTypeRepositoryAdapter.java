package br.com.selectgearmotors.company.application.database.repository;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.company.application.database.mapper.CompanyTypeMapper;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.out.CompanyTypeRepositoryPort;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CompanyTypeRepositoryAdapter implements CompanyTypeRepositoryPort {

    private final CompanyTypeRepository companyTypeRepository;
    private final CompanyTypeMapper companyTypeMapper;

    @Autowired
    public CompanyTypeRepositoryAdapter(CompanyTypeRepository companyTypeRepository, CompanyTypeMapper companyTypeMapper) {
        this.companyTypeRepository = companyTypeRepository;
        this.companyTypeMapper = companyTypeMapper;
    }

    @Override
    public CompanyType save(CompanyType companyType) {
        try {
            CompanyTypeEntity productCategoryEntity = companyTypeMapper.fromModelTpEntity(companyType);
            CompanyTypeEntity saved = companyTypeRepository.save(productCategoryEntity);
            validateSavedEntity(saved);
            return companyTypeMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             companyTypeRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public CompanyType findById(Long id) {
        Optional<CompanyTypeEntity> buCompanyType = companyTypeRepository.findById(id);
        if (buCompanyType.isPresent()) {
            return companyTypeMapper.fromEntityToModel(buCompanyType.get());
        }
        return null;
    }

    @Override
    public List<CompanyType> findAll() {
        List<CompanyTypeEntity> all = companyTypeRepository.findAll();
        return companyTypeMapper.map(all);
    }

    @Override
    public CompanyType update(Long id, CompanyType companyType) {
        Optional<CompanyTypeEntity> resultById = companyTypeRepository.findById(id);
        if (resultById.isPresent()) {
            CompanyTypeEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return companyTypeMapper.fromEntityToModel(companyTypeRepository.save(productCategoryToChange));
        }
        return null;
    }

    private void validateSavedEntity(CompanyTypeEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }

        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: nome do produto é nulo");
        }
    }
}
