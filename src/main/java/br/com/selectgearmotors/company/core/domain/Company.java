package br.com.selectgearmotors.company.core.domain;

import br.com.selectgearmotors.company.commons.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "Company", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, companyTypeId"})
@Tag(name = "Company", description = "Model")
public class Company implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    private String fantasyName;

    @Schema(description = "code of the Product.",
            example = "V$")
    private String code;

    private String email;

    private String mobile;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String description;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long mediaId;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String address;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Boolean dataProcessingConsent;

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE)
    private LocalDate foundationDate; //Data de Fundação

    @Schema(description = "CNPJ number of the Company.", example = "12.345.678/0001-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "CNPJ number")
    private String companyId;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long companyTypeId;

    public void update(Long id, Company company) {
        this.id = id;
        this.socialName = company.getSocialName();
        this.fantasyName = company.getFantasyName();
        this.code = company.getCode();
        this.email = company.getEmail();
        this.mobile = company.getMobile();
        this.description = company.getDescription();
        this.mediaId = company.getMediaId();
        this.address = company.getAddress();
        this.dataProcessingConsent = company.getDataProcessingConsent();
        this.companyTypeId = company.getCompanyTypeId();
        this.foundationDate = company.getFoundationDate();
        this.companyId = company.getCompanyId();
    }
}
