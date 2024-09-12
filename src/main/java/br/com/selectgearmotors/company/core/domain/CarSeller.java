package br.com.selectgearmotors.company.core.domain;

import br.com.selectgearmotors.company.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "CarSellerEntity", requiredProperties = {"socialId"})
public class CarSeller implements Serializable {

    @Schema(description = "Unique identifier of the Client.",
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
    @Pattern(regexp = "^(\\d{2}\\x2E\\d{3}\\x2E\\d{3}[-]\\d{1})$|^(\\d{2}\\x2E\\d{3}\\x2E\\d{3})$", message = "RG com formato Errado")
    private String documentId; //RG

    @Schema(description = "crp number of the Psychological.",
            example = "SP", required = false)
    @Pattern(regexp = "^[A-Z]{2}$", message = "Mobile Phone number")
    private String documentDistrict;

    @Schema(description = "documentDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    private LocalDate documentDispatchDate;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    private LocalDate birthDate; //Data de Nascimento

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long companyId;

    @Schema(description = "Client of the User.",
            example = "1", ref = "ClientCategoryEntity")
    private Long mediaId;

    public void update(Long id, CarSeller carSeller) {
        this.id = id;
        this.name = carSeller.getName();
        this.email = carSeller.getEmail();
        this.code = carSeller.getCode();
        this.mobile = carSeller.getMobile();
        this.socialId = carSeller.getSocialId();
        this.socialIdDispatchDate = carSeller.getSocialIdDispatchDate();
        this.documentId = carSeller.getDocumentId();
        this.documentDistrict = carSeller.getDocumentDistrict();
        this.documentDispatchDate = carSeller.getDocumentDispatchDate();
        this.birthDate = carSeller.getBirthDate();
        this.companyId = carSeller.getCompanyId();
        this.mediaId = carSeller.getMediaId();
    }
}
