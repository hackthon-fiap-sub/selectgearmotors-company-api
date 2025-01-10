package br.com.selectgearmotors.vehiclereservation.core.service;

import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;
import br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation.*;
import br.com.selectgearmotors.vehiclereservation.core.ports.out.ReservationRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ReservationService implements CreateReservationPort, UpdateReservationPort, FindByIdReservationPort, FindReservationsPort, DeleteReservationPort {

    private final ReservationRepositoryPort reservationRepositoryPort;

    @Autowired
    public ReservationService(ReservationRepositoryPort reservationRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation reservationResponse = findByVehicleId(reservation.getVehicleId());
        if (reservationResponse != null) {
            throw new ResourceFoundException("Reservation already exists");
        }
        return reservationRepositoryPort.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation product) {
        Reservation resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return reservationRepositoryPort.save(resultById);
        }
        return null;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepositoryPort.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
       return reservationRepositoryPort.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Reservation carSellerById = findById(id);
            if (carSellerById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            reservationRepositoryPort.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public Reservation findByVehicleId(UUID vehicleId) {
        return reservationRepositoryPort.findByVehicleId(vehicleId);
    }

    @Override
    public Reservation findByBuyerId(UUID vehicleId) {
        return reservationRepositoryPort.findByBuyerId(vehicleId);
    }
}
