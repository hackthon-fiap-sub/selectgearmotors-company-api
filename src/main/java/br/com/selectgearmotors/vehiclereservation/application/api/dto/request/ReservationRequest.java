package br.com.selectgearmotors.vehiclereservation.application.api.dto.request;

import br.com.selectgearmotors.vehiclereservation.core.domain.StatusReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ReservationRequest", requiredProperties = {"vehicleId, compradorId, status"})
@Tag(name = "ReservationRequest", description = "Model")
public class ReservationRequest implements Serializable {

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    private UUID vehicleId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    private UUID buyerId;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    private StatusReservation statusReservation;

}
