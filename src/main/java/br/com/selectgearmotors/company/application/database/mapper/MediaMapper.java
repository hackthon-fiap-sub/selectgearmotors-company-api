package br.com.selectgearmotors.company.application.database.mapper;

import br.com.selectgearmotors.company.core.domain.Media;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "path", target = "path")
    @Mapping(source = "mediaType", target = "mediaType")
    MediaEntity fromModelTpEntity(Media media);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    Media fromEntityToModel(MediaEntity mediaEntity);

    List<Media> map(List<MediaEntity> mediaEntities);
}
