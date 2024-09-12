package br.com.selectgearmotors.company.application.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyResponse;
import br.com.selectgearmotors.company.commons.util.CompanyIdFormatter;
import br.com.selectgearmotors.company.core.domain.Company;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CompanyIdFormatter.class)
public interface CompanyApiMapper {

    @Mapping(source = "socialName", target = "socialName")
    @Mapping(source = "fantasyName", target = "fantasyName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "mediaId", target = "mediaId")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "dataProcessingConsent", target = "dataProcessingConsent")
    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "foundationDate", target = "foundationDate")
    @Mapping(source = "companyTypeId", target = "companyTypeId")
    Company fromRequest(CompanyRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "mobile", source = "mobile")
    @Mapping(target = "companyTypeId", source = "companyTypeId")
    @Mapping(target = "companyId", source = "companyId", qualifiedByName = "revertCompanyId")
    @Mapping(target = "mediaId", source = "mediaId")
    CompanyResponse fromEntity(Company company);

    List<CompanyResponse> map(List<Company> companies);
}
