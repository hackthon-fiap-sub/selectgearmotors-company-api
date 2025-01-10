package br.com.selectgearmotors.vehiclereservation.infrastructure.repository;

import br.com.selectgearmotors.vehiclereservation.infrastructure.entity.reservation.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<ReservationEntity> findByVehicleId(UUID vehicleId);
    Optional<ReservationEntity> findByBuyerId(UUID buyerId);
}
