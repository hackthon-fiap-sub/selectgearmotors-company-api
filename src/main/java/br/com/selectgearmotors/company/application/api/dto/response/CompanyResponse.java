package br.com.selectgearmotors.company.application.api.dto.response;

import br.com.selectgearmotors.company.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Schema(description = "CompanyResponse", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, companyTypeEntity"})
@Tag(name = "CompanyResponse", description = "Model")
public class CompanyResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String fantasyName;

    private String email;

    @Schema(description = "Name of the Product.",
            example = "Vicente")
    @Size(min = 3, max = 255)
    private String code;

    @Schema(description = "Description of the Product.",
            example = "Vicente")
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "value the Product.",
            example = "V$")
    private String mediaId;

    private String mobile;

    private String companyTypeId;

    @Schema(description = "Name of the Product Category.",
            example = "Bebida")
    @Size(min = 3, max = 255)
    private String companyId;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    private LocalDate foundationDate; //Data de Fundação
}
