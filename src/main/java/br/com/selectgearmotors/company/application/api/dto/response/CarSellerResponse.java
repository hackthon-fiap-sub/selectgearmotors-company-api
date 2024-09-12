package br.com.selectgearmotors.company.application.api.dto.response;

import br.com.selectgearmotors.company.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CarSellerRequest", requiredProperties = {"id", "name", "email", "mobile", "socialId", "socialIdDispatchDate", "documentId", "documentDistrict", "documentDispatchDate", "birthDate", "companyId", "mediaId"})
@Tag(name = "CarSellerRequest", description = "Model")
public class CarSellerResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Size(min = 3, max = 255)
    private String code;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    private String name;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"email\" é obrigario")
    @Size(min = 1, max = 255)
    private String email;

    @Schema(description = "Mobile Phone number of the Driver.",
            example = "(99) 9999-9999", required = true)
    @Pattern(regexp = "^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$", message = "Mobile Phone number")
    @NotNull
    @Size(max = 15)
    private String mobile;

    @Schema(description = "crp number of the Psychological.",
            example = "Jessica Abigail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Column(name = "social_id")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "CPF number")
    private String socialId; //CPF

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    private LocalDate socialIdDispatchDate; //CPF

    @Schema(description = "RG number of the Psychological.",
            example = "SP 9999999", required = false)
    @Column(name = "document_id")
    @Pattern(regexp = "^(\\d{2}\\x2E\\d{3}\\x2E\\d{3}[-]\\d{1})$|^(\\d{2}\\x2E\\d{3}\\x2E\\d{3})$", message = "RG com formato Errado")
    private String documentId; //RG

    @Schema(description = "crp number of the Psychological.",
            example = "SP", required = false)
    @Column(name = "document_district")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Mobile Phone number")
    private String documentDistrict;

    @Schema(description = "documentDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @Column(name = "document_dispatch_date")
    private LocalDate documentDispatchDate;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @Column(name = "birth_date")
    private LocalDate birthDate; //Data de Nascimento

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long companyId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long mediaId;
}
