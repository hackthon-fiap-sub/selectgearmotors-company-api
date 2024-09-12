package br.com.selectgearmotors.company.core.ports.out;

import br.com.selectgearmotors.company.core.domain.CarSeller;

import java.util.List;

public interface CarSellerRepositoryPort {
    CarSeller save(CarSeller carSeller);
    boolean remove(Long id);
    CarSeller findById(Long id);
    List<CarSeller> findAll();
    CarSeller update(Long id, CarSeller carSeller);
    CarSeller findByCompanyId(Long id);
    CarSeller findByEmail(String email);
    CarSeller findByCode(String code);
}
