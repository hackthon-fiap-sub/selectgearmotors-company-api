package br.com.selectgearmotors.vehiclereservation.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Reservation", requiredProperties = {"socialId"})
public class Reservation implements Serializable {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    private Long id;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Column(name = "buyer_id", nullable = false)
    private UUID buyerId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusReservation statusReservation;

    public void update(Long id, Reservation reservation) {
        this.id = id;
        this.vehicleId = reservation.getVehicleId();
        this.buyerId = reservation.getBuyerId();
        this.statusReservation = reservation.getStatusReservation();
    }
}
