package br.com.selectgearmotors.vehiclereservation.application.api.mapper;

import br.com.selectgearmotors.vehiclereservation.application.api.dto.request.ReservationRequest;
import br.com.selectgearmotors.vehiclereservation.application.api.dto.response.ReservationResponse;
import br.com.selectgearmotors.vehiclereservation.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface ReservationApiMapper {

    @Mapping(source = "vehicleId", target = "vehicleId")
    @Mapping(source = "buyerId", target = "buyerId")
    @Mapping(source = "statusReservation", target = "statusReservation")
    Reservation fromRequest(ReservationRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ReservationResponse fromEntity(Reservation reservation);

   List<ReservationResponse> map(List<Reservation> reservations);
}
