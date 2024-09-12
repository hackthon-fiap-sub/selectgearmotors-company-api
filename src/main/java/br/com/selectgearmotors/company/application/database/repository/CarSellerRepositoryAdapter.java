package br.com.selectgearmotors.company.application.database.repository;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.company.application.database.mapper.CarSellerMapper;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.ports.out.CarSellerRepositoryPort;
import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CarSellerRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CarSellerRepositoryAdapter implements CarSellerRepositoryPort {

    private final CarSellerRepository carSellerRepository;
    private final CarSellerMapper carSellerMapper;
    private final CompanyRepository companyRepository;

    @Autowired
    public CarSellerRepositoryAdapter(CarSellerRepository carSellerRepository, CarSellerMapper carSellerMapper, CompanyRepository companyRepository) {
        this.carSellerRepository = carSellerRepository;
        this.carSellerMapper = carSellerMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public CarSeller save(CarSeller carSeller) {
        try {
            CarSellerEntity carSellerEntity = carSellerMapper.fromModelTpEntity(carSeller);
            Long companyId = carSeller.getCompanyId();

            if (companyId != null) {
                Optional<CompanyEntity> companyLegalEntityById = companyRepository.findById(companyId);
                if (!companyLegalEntityById.isPresent()) {
                    throw new ResourceFoundException("Cliente já cadastrado");
                }
                carSellerEntity.setCompanyEntity(companyLegalEntityById.get());
            }

            CarSellerEntity saved = carSellerRepository.save(carSellerEntity);
            validateSavedEntity(saved);
            return carSellerMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             carSellerRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public CarSeller findById(Long id) {
        Optional<CarSellerEntity> buCarSeller = carSellerRepository.findById(id);
        if (buCarSeller.isPresent()) {
            return carSellerMapper.fromEntityToModel(buCarSeller.get());
        }
        return null;
    }

    @Override
    public List<CarSeller> findAll() {
        List<CarSellerEntity> all = carSellerRepository.findAll();
        return carSellerMapper.map(all);
    }

    @Override
    public CarSeller update(Long id, CarSeller carSeller) {
        Optional<CarSellerEntity> resultById = carSellerRepository.findById(id);
        if (resultById.isPresent()) {
            CarSellerEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return carSellerMapper.fromEntityToModel(carSellerRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public CarSeller findByCompanyId(Long id) {
        Optional<CarSellerEntity> resultById = carSellerRepository.findByCompanyEntityId(id);
        if (resultById.isPresent()) {
            CarSellerEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return carSellerMapper.fromEntityToModel(carSellerRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public CarSeller findByEmail(String email) {
        Optional<CarSellerEntity> buCarSeller = carSellerRepository.findByEmail(email);
        if (buCarSeller.isPresent()) {
            return carSellerMapper.fromEntityToModel(buCarSeller.get());
        }
        return null;
    }

    @Override
    public CarSeller findByCode(String code) {
        Optional<CarSellerEntity> buCarSeller = carSellerRepository.findByCode(code);
        if (buCarSeller.isPresent()) {
            return carSellerMapper.fromEntityToModel(buCarSeller.get());
        }
        return null;
    }

    private void validateSavedEntity(CarSellerEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }
    }
}
