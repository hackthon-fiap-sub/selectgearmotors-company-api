package br.com.selectgearmotors.vehiclereservation.infrastructure.entity.reservation;

import br.com.selectgearmotors.vehiclereservation.core.domain.StatusReservation;
import br.com.selectgearmotors.vehiclereservation.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_reservation", schema = "vehiclereservation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ReservationEntity", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, companyTypeEntity"})
public class ReservationEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"vehicleId\" é obrigario")
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"comprador_id\" é obrigario")
    @Column(name = "buyer_id", nullable = false)
    private UUID buyerId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"status\" é obrigario")
    @Column(name = "status_reservation", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusReservation statusReservation;

    public void update(Long id, ReservationEntity reservationEntity) {
        this.id = id;
        this.vehicleId = reservationEntity.getVehicleId();
        this.buyerId = reservationEntity.getBuyerId();
        this.statusReservation = reservationEntity.getStatusReservation();
    }
}
