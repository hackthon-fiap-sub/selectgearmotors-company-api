package br.com.selectgearmotors.company.core.ports.out;

import br.com.selectgearmotors.company.core.domain.Media;
import java.util.List;

public interface MediaRepositoryPort {
    Media save(Media media);
    boolean remove(Long id);
    Media findById(Long id);
    List<Media> findAll();
    Media update(Long id, Media media);
    Media findByName(String name);
}
