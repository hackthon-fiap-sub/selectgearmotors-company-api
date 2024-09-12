package br.com.selectgearmotors.company.application.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CarSellerRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CarSellerResponse;
import br.com.selectgearmotors.company.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface CarSellerApiMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "socialId", target = "socialId")
    @Mapping(source = "socialIdDispatchDate", target = "socialIdDispatchDate")
    @Mapping(source = "documentDistrict", target = "documentDistrict")
    @Mapping(source = "documentDispatchDate", target = "documentDispatchDate")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "mediaId ", target = "mediaId")
    @Mapping(source = "companyId", target = "companyId")
    CarSeller fromRequest(CarSellerRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(source = "socialId", target = "socialId", qualifiedByName = "formatSocialId")
    CarSellerResponse fromEntity(CarSeller carSeller);

   List<CarSellerResponse> map(List<CarSeller> carSellers);
}
