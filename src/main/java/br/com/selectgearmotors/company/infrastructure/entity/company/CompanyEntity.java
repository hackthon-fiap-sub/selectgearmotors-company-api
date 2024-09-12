package br.com.selectgearmotors.company.infrastructure.entity.company;

import br.com.selectgearmotors.company.commons.Constants;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.entity.domain.AuditDomain;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tb_company", schema = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CompanyEntity", requiredProperties = {"id, code, name, email, mobile, mediaEntity, description, address, dataProcessingConsent, companyTypeEntity, socialName, fantasyName, companyId, foundation"})
@Tag(name = "CompanyEntity", description = "Model")
public class CompanyEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Company.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    @Column(name = "social_name")
    private String socialName;

    @Schema(description = "Fantasy Name of the Cooperative.",
            example = "SCRAP LTDA", required = false)
    @Column(name = "fantasy_name")
    private String fantasyName;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255)
    private String code;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"email\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Schema(description = "Mobile Phone number of the Driver.",
            example = "(99) 9999-9999", required = true)
    @Pattern(regexp = "^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$", message = "Mobile Phone number")
    @NotNull
    @Size(max = 15)
    private String mobile;

    @Schema(description = "Media of the User.",
            example = "1", required = true, ref = "User")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "media_id", unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MediaEntity mediaEntity;

    @Schema(description = "description of the Company.",
            example = "V$")
    @Size(min = 0, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Schema(description = "description of the Company.",
            example = "V$")
    @Size(min = 0, max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Schema(description = "description of the Company.",
            example = "V$")
    @Column(name = "data_processing_consent")
    private Boolean dataProcessingConsent;

    @Schema(description = "CompanyId number of the Cooperative.",
            example = "76.438.848/0001-69", required = false)
    @Column(name = "company_id")
    @NotNull
    @Size(max = 14)
    private String companyId; //CNPJ

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @Column(name = "foundation_date")
    private LocalDate foundationDate; //Data de Fundação

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "CompanyCategoryEntity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_type_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CompanyTypeEntity companyTypeEntity;

    public void update(Long id, CompanyEntity companyEntity) {
        this.id = id;
        this.socialName = companyEntity.getSocialName();
        this.fantasyName = companyEntity.getFantasyName();
        this.code = companyEntity.getCode();
        this.email = companyEntity.getEmail();
        this.mobile = companyEntity.getMobile();
        this.mediaEntity = companyEntity.getMediaEntity();
        this.description = companyEntity.getDescription();
        this.address = companyEntity.getAddress();
        this.dataProcessingConsent = companyEntity.getDataProcessingConsent();
        this.companyTypeEntity = companyEntity.getCompanyTypeEntity();
        this.companyId = companyEntity.getCompanyId();
        this.foundationDate = companyEntity.getFoundationDate();
    }
}
