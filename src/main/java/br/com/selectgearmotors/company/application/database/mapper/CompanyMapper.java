package br.com.selectgearmotors.company.application.database.mapper;

import br.com.selectgearmotors.company.commons.util.CompanyIdFormatter;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyIdFormatter.class})
public interface CompanyMapper {

    @Mapping(source = "socialName", target = "socialName")
    @Mapping(source = "fantasyName", target = "fantasyName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "companyId", target = "companyId", qualifiedByName = "formatCompanyId")
    @Mapping(source = "dataProcessingConsent", target = "dataProcessingConsent")
    @Mapping(source = "companyTypeId", target = "companyTypeEntity.id")
    @Mapping(source = "mediaId", target = "mediaEntity.id")
    CompanyEntity fromModelTpEntity(Company company);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "mobile", source = "mobile")
    @Mapping(target = "companyTypeId", source = "companyTypeEntity.id")
    @Mapping(target = "mediaId", source = "mediaEntity.id")
    Company fromEntityToModel(CompanyEntity companyEntity);

    List<Company> map(List<CompanyEntity> companyEntities);
}
