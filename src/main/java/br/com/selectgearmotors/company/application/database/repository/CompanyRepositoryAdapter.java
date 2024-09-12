package br.com.selectgearmotors.company.application.database.repository;

import br.com.selectgearmotors.company.application.database.mapper.CompanyMapper;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.ports.out.CompanyRepositoryPort;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class CompanyRepositoryAdapter implements CompanyRepositoryPort {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyRepositoryAdapter(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public Company save(Company company) {
        try {
            CompanyEntity companyEntity = companyMapper.fromModelTpEntity(company);
            if (companyEntity != null) {
                companyEntity.setCode(UUID.randomUUID().toString());
                CompanyEntity saved = companyRepository.save(companyEntity);
                return companyMapper.fromEntityToModel(saved);
            }
        } catch (Exception e) {
            log.info("Erro ao salvar produto: " + e.getMessage());
            return null;
        }

        return null;
    }

    @Override
    public boolean remove(Long id) {
        try {
            companyRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Company findById(Long id) {
        Optional<CompanyEntity> buCompany = companyRepository.findById(id);
        if (buCompany.isPresent()) {
            return companyMapper.fromEntityToModel(buCompany.get());
        }
        return null;
    }

    @Override
    public List<Company> findAll() {
        List<CompanyEntity> all = companyRepository.findAll();
        return companyMapper.map(all);
    }

    @Override
    public Company update(Long id, Company company) {
        Optional<CompanyEntity> resultById = companyRepository.findById(id);
        if (resultById.isPresent()) {

            CompanyEntity companyToChange = resultById.get();
            companyToChange.update(id, companyToChange);

            return companyMapper.fromEntityToModel(companyRepository.save(companyToChange));
        }

        return null;
    }

    @Override
    public Company findByCode(String code) {
        CompanyEntity byCode = companyRepository.findByCode(code);
        return companyMapper.fromEntityToModel(byCode);
    }

    @Override
    public Company findByName(String name) {
        CompanyEntity byCode = companyRepository.findBySocialName(name);
        return companyMapper.fromEntityToModel(byCode);
    }

    @Override
    public Company findByEmail(String email) {
        CompanyEntity byEmail = companyRepository.findByEmail(email);
        return companyMapper.fromEntityToModel(byEmail);
    }
}
