package br.com.selectgearmotors.company.application.api.dto.request;

import br.com.selectgearmotors.company.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Schema(description = "ProductRequest", requiredProperties = {"name, email, mobile, mediaId, description, socialId, address, dataProcessingConsent, companyTypeId, socialName, fantasyName, companyId, foundationDate"})
@Tag(name = "ProductRequest", description = "Model")
public class CompanyRequest implements Serializable {

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String fantasyName;

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

    @Schema(description = "picture of the Company.",
            example = "V$")
    private Long mediaId;

    @Schema(description = "description of the Company.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "description of the Company.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String address;

    @Schema(description = "description of the Company.",
            example = "V$")
    private Boolean dataProcessingConsent;

    @Schema(description = "Product Category of the Product.",
            example = "Bebida", ref = "ProductCategory")
    private Long companyTypeId;

    @Schema(description = "Name of the Product Category.",
            example = "Bebida")
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "CNPJ Inválido")
    private String companyId;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    private LocalDate foundationDate; //Data de Fundação
}
