package br.com.selectgearmotors.company.application.database.mapper;

import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {

    @Mapping(source = "name", target = "name")
    CompanyTypeEntity fromModelTpEntity(CompanyType companyType);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    CompanyType fromEntityToModel(CompanyTypeEntity productCategoryEntity);

    List<CompanyType> map(List<CompanyTypeEntity> productCategoryEntities);
}
