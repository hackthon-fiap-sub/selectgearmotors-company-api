package br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation;

import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;

import java.util.UUID;

public interface FindByIdReservationPort {
    Reservation findById(Long id);
    Reservation findByVehicleId(UUID vehicleId);
    Reservation findByBuyerId(UUID vehicleId);
}
