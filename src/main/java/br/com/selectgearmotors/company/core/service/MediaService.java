package br.com.selectgearmotors.company.core.service;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.core.domain.Media;
import br.com.selectgearmotors.company.core.ports.in.media.*;
import br.com.selectgearmotors.company.core.ports.out.MediaRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MediaService implements CreateMediaPort, UpdateMediaPort, FindByIdMediaPort, FindMediasPort, DeleteMediaPort {

    private final MediaRepositoryPort companyRepository;

    @Override
    public Media save(Media company) throws ResourceFoundException {
        return companyRepository.save(company);
    }

    @Override
    public Media update(Long id, Media company) {
        Media resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, company);

            return companyRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Media findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Media> findAll() {
       return companyRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Media companyById = findById(id);
            if (companyById == null) {
                throw new ResourceFoundException("Media not found");
            }

            companyRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
