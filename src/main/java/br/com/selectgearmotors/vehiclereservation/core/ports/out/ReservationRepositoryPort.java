package br.com.selectgearmotors.vehiclereservation.core.ports.out;

import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    boolean remove(Long id);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    Reservation findByVehicleId(UUID vehicleId);
    Reservation findByBuyerId(UUID buyerId);
}
