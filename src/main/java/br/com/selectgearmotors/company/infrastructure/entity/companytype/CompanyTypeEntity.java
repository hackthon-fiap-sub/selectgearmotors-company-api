package br.com.selectgearmotors.company.infrastructure.entity.companytype;

import br.com.selectgearmotors.company.infrastructure.entity.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_company_type", schema = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "CompanyTypeEntity", requiredProperties = {"id, name"})
@Tag(name = "CompanyTypeEntity", description = "Model")
public class CompanyTypeEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255)
    private String name;

    public void update(Long id, CompanyTypeEntity companyTypeEntity) {
        this.id = id;
        this.name = companyTypeEntity.getName();
    }
}
