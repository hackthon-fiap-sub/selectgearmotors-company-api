package br.com.selectgearmotors.company.core.ports.in.media;

import br.com.selectgearmotors.company.core.domain.Media;

public interface UpdateMediaPort {
    Media update(Long id, Media media);
}
