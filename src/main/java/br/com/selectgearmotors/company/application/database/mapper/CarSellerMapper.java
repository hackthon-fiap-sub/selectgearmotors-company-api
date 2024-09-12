package br.com.selectgearmotors.company.application.database.mapper;

import br.com.selectgearmotors.company.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface CarSellerMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "companyId", target = "companyEntity.id")
    @Mapping(source = "socialId", target = "socialId", qualifiedByName = "formatSocialId")
    @Mapping(source = "socialIdDispatchDate", target = "socialIdDispatchDate")
    @Mapping(source = "documentDistrict", target = "documentDistrict")
    @Mapping(source = "documentDispatchDate", target = "documentDispatchDate")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "mediaId ", target = "mediaEntity.id")
    CarSellerEntity fromModelTpEntity(CarSeller carSeller);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyId", source = "companyEntity.id")
    @Mapping(target = "mediaId", source = "mediaEntity.id")
    CarSeller fromEntityToModel(CarSellerEntity carSellerEntity);

    List<CarSeller> map(List<CarSellerEntity> productCategoryEntities);
}
