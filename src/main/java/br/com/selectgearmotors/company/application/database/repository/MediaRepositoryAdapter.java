package br.com.selectgearmotors.company.application.database.repository;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.company.application.database.mapper.MediaMapper;
import br.com.selectgearmotors.company.core.domain.Media;
import br.com.selectgearmotors.company.core.ports.out.MediaRepositoryPort;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.company.infrastructure.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MediaRepositoryAdapter implements MediaRepositoryPort {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Autowired
    public MediaRepositoryAdapter(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public Media save(Media media) {
        try {
            MediaEntity productCategoryEntity = mediaMapper.fromModelTpEntity(media);
            MediaEntity saved = mediaRepository.save(productCategoryEntity);
            validateSavedEntity(saved);
            return mediaMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             mediaRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Media findById(Long id) {
        Optional<MediaEntity> buMedia = mediaRepository.findById(id);
        if (buMedia.isPresent()) {
            return mediaMapper.fromEntityToModel(buMedia.get());
        }
        return null;
    }

    @Override
    public List<Media> findAll() {
        List<MediaEntity> all = mediaRepository.findAll();
        return mediaMapper.map(all);
    }

    @Override
    public Media update(Long id, Media media) {
        Optional<MediaEntity> resultById = mediaRepository.findById(id);
        if (resultById.isPresent()) {
            MediaEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return mediaMapper.fromEntityToModel(mediaRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public Media findByName(String name) {
        MediaEntity byName = mediaRepository.findByName(name);
        if (byName == null) {
            throw new ResourceFoundException("Produto não encontrado no repositorio");
        }
        return mediaMapper.fromEntityToModel(byName);
    }

    private void validateSavedEntity(MediaEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }

        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: nome do produto é nulo");
        }
    }
}
