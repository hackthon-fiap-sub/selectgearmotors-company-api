package br.com.selectgearmotors.company.core.ports.in.carseller;

import br.com.selectgearmotors.company.core.domain.CarSeller;

public interface UpdateCarSellerPort {
    CarSeller update(Long id, CarSeller carSeller);
}
