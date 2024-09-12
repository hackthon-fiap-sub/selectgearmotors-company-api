package br.com.selectgearmotors.company.application.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyTypeRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyTypeResponse;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyTypeApiMapper {

    @Mapping(source = "name", target = "name")
    CompanyType fromRequest(CompanyTypeRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    CompanyTypeResponse fromEntity(CompanyType companyType);

   List<CompanyTypeResponse> map(List<CompanyType> companyTypes);
}
