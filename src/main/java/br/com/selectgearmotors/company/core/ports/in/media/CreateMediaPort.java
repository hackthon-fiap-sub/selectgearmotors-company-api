package br.com.selectgearmotors.company.core.ports.in.media;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.domain.Media;

public interface CreateMediaPort {
    Media save(Media media) throws ResourceFoundException;
}
