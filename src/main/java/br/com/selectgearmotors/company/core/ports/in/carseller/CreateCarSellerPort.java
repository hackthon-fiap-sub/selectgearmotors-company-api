package br.com.selectgearmotors.company.core.ports.in.carseller;

import br.com.selectgearmotors.company.core.domain.CarSeller;

public interface CreateCarSellerPort {
    CarSeller save(CarSeller carSeller);
}
