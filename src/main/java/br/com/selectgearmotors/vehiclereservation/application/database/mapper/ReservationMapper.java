package br.com.selectgearmotors.vehiclereservation.application.database.mapper;

import br.com.selectgearmotors.vehiclereservation.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;
import br.com.selectgearmotors.vehiclereservation.infrastructure.entity.reservation.ReservationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface ReservationMapper {

    @Mapping(source = "vehicleId", target = "vehicleId")
    @Mapping(source = "buyerId", target = "buyerId")
    @Mapping(source = "statusReservation", target = "statusReservation")
    ReservationEntity fromModelTpEntity(Reservation reservation);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    Reservation fromEntityToModel(ReservationEntity reservationEntity);

    List<Reservation> map(List<ReservationEntity> reservationEntities);
}
