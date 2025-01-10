package br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation;

import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;

import java.util.List;

public interface FindReservationsPort {
    List<Reservation> findAll();
}
