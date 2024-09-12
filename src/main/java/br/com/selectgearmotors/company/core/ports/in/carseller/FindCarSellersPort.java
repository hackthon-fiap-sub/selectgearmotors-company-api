package br.com.selectgearmotors.company.core.ports.in.carseller;

import br.com.selectgearmotors.company.core.domain.CarSeller;

import java.util.List;

public interface FindCarSellersPort {
    List<CarSeller> findAll();
}
