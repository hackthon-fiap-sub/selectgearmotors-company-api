package br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation;

import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;

public interface UpdateReservationPort {
    Reservation update(Long id, Reservation reservation);
}
