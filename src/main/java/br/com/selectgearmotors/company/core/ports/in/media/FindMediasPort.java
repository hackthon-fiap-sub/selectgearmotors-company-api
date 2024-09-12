package br.com.selectgearmotors.company.core.ports.in.media;

import br.com.selectgearmotors.company.core.domain.Media;
import java.util.List;

public interface FindMediasPort {
    List<Media> findAll();
}
