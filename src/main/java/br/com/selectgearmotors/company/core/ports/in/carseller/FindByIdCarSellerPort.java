package br.com.selectgearmotors.company.core.ports.in.carseller;

import br.com.selectgearmotors.company.core.domain.CarSeller;

public interface FindByIdCarSellerPort {
    CarSeller findById(Long id);
    CarSeller findByCompanyId(Long id);
    CarSeller findByCode(String code);
    CarSeller findByEmail(String email);
}
