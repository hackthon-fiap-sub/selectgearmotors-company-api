package br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation;

import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;

public interface CreateReservationPort {
    Reservation save(Reservation reservation);
}
