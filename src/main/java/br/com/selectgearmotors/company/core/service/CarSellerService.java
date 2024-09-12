package br.com.selectgearmotors.company.core.service;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.ports.in.carseller.*;
import br.com.selectgearmotors.company.core.ports.out.CarSellerRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CarSellerService implements CreateCarSellerPort, UpdateCarSellerPort, FindByIdCarSellerPort, FindCarSellersPort, DeleteCarSellerPort {

    private final CarSellerRepositoryPort carSellerRepository;

    @Autowired
    public CarSellerService(CarSellerRepositoryPort carSellerRepository) {
        this.carSellerRepository = carSellerRepository;
    }

    @Override
    public CarSeller save(CarSeller carSeller) {
        CarSeller byEmail = findByEmail(carSeller.getEmail());
        if (byEmail != null) {
            throw new ResourceFoundException("CarSeller already exists");
        }
        carSeller.setCode(UUID.randomUUID().toString());
        return carSellerRepository.save(carSeller);
    }

    @Override
    public CarSeller update(Long id, CarSeller product) {
        CarSeller resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return carSellerRepository.save(resultById);
        }
        return null;
    }

    @Override
    public CarSeller findByEmail(String email) {
        return carSellerRepository.findByEmail(email);
    }

    @Override
    public CarSeller findById(Long id) {
        return carSellerRepository.findById(id);
    }

    @Override
    public CarSeller findByCode(String code) {
        return carSellerRepository.findByCode(code);
    }

    @Override
    public CarSeller findByCompanyId(Long id) {
        return carSellerRepository.findByCompanyId(id);
    }

    @Override
    public List<CarSeller> findAll() {
       return carSellerRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            CarSeller carSellerById = findById(id);
            if (carSellerById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            carSellerRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
