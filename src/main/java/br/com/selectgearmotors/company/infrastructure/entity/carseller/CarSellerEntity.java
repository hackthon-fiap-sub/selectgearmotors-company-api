package br.com.selectgearmotors.company.infrastructure.entity.carseller;

import br.com.selectgearmotors.company.commons.Constants;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.domain.AuditDomain;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tb_car_seller", schema = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CarSellerEntity", requiredProperties = {"id, code, name, email, mobile, pic, description, socialId, address, companyTypeEntity"})
public class CarSellerEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Client.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255)
    private String code;

    @Schema(description = "name of the Company.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255)
    private String name;

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

    @Schema(description = "crp number of the Psychological.",
            example = "Jessica Abigail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Column(name = "social_id")
    private String socialId; //CPF

    @Schema(description = "socialIdDispatchDate of the Psychological.",
            format = "ISO8601 date string",
            example = "13/09/2022", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @DateTimeFormat(pattern = Constants.BRAZILIAN_DATE_WITHOUT_TOME)
    @Column(name = "social_id_dispatch_date")
    private LocalDate socialIdDispatchDate; //CPF

    @Schema(description = "RG number of the Psychological.",
            example = "SP 9999999", required = false)
    @Column(name = "document_id")
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
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity companyEntity;

    @Schema(description = "Media of the User.",
            example = "1", required = true, ref = "User")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "media_id", unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MediaEntity mediaEntity;

    public void update(Long id, CarSellerEntity carSellerEntity) {
        this.id = id;
        this.code = carSellerEntity.getCode();
        this.name = carSellerEntity.getName();
        this.email = carSellerEntity.getEmail();
        this.mobile = carSellerEntity.getMobile();
        this.socialId = carSellerEntity.getSocialId();
        this.socialIdDispatchDate = carSellerEntity.getSocialIdDispatchDate();
        this.documentId = carSellerEntity.getDocumentId();
        this.documentDistrict = carSellerEntity.getDocumentDistrict();
        this.documentDispatchDate = carSellerEntity.getDocumentDispatchDate();
        this.birthDate = carSellerEntity.getBirthDate();
        this.companyEntity = carSellerEntity.getCompanyEntity();
        this.mediaEntity = carSellerEntity.getMediaEntity();
    }
}
