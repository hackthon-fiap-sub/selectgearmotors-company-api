package br.com.selectgearmotors.company.database;

import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.company.application.database.mapper.MediaMapper;
import br.com.selectgearmotors.company.application.database.repository.MediaRepositoryAdapter;
import br.com.selectgearmotors.company.core.domain.Media;
import br.com.selectgearmotors.company.infrastructure.entity.domain.MediaType;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.company.infrastructure.repository.MediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MediaRepositoryAdapterTest {

    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private MediaMapper mediaMapper;

    @InjectMocks
    private MediaRepositoryAdapter mediaRepositoryAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    public void testSave_Success() {
        Media media = new Media();
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setMediaType(MediaType.JPG);
        mediaEntity.setName("rogeriofontes.jpeg");
        mediaEntity.setPath("https://wagon-driver-images.s3.amazonaws.com/rogeriofontes.jpeg");

        // Configurar o comportamento dos mocks
        when(mediaMapper.fromModelTpEntity(media)).thenReturn(mediaEntity);
        when(mediaRepository.save(mediaEntity)).thenReturn(mediaEntity);
        when(mediaMapper.fromEntityToModel(mediaEntity)).thenReturn(media);

        // Executa o método a ser testado
        Media result = mediaRepositoryAdapter.save(media);

        // Verifica se o método save foi chamado e se o resultado está correto
        assertNotNull(result);
        verify(mediaRepository, times(1)).save(mediaEntity);
    }

    @Disabled
    public void testSave_ThrowsException() {
        Media media = new Media();
        when(mediaMapper.fromModelTpEntity(media)).thenReturn(null);

        assertThrows(ResourceFoundException.class, () -> {
            mediaRepositoryAdapter.save(media);
        });

        verify(mediaRepository, never()).save(any(MediaEntity.class)); // Verifica que o repositório nunca foi chamado
    }

    @Test
    public void testRemove_Success() {
        doNothing().when(mediaRepository).deleteById(1L);

        boolean result = mediaRepositoryAdapter.remove(1L);

        assertTrue(result);
        verify(mediaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRemove_Failure() {
        doThrow(new ResourceNotRemoveException("Erro ao remover")).when(mediaRepository).deleteById(1L);

        boolean result = mediaRepositoryAdapter.remove(1L);

        assertFalse(result);
        verify(mediaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindById_Success() {
        MediaEntity mediaEntity = new MediaEntity();
        Media media = new Media();

        when(mediaRepository.findById(1L)).thenReturn(Optional.of(mediaEntity));
        when(mediaMapper.fromEntityToModel(mediaEntity)).thenReturn(media);

        Media result = mediaRepositoryAdapter.findById(1L);

        assertNotNull(result);
        verify(mediaRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(mediaRepository.findById(1L)).thenReturn(Optional.empty());

        Media result = mediaRepositoryAdapter.findById(1L);

        assertNull(result);
        verify(mediaRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        List<MediaEntity> mediaEntities = List.of(new MediaEntity(), new MediaEntity());
        List<Media> mediaList = List.of(new Media(), new Media());

        when(mediaRepository.findAll()).thenReturn(mediaEntities);
        when(mediaMapper.map(mediaEntities)).thenReturn(mediaList);

        List<Media> result = mediaRepositoryAdapter.findAll();

        assertEquals(2, result.size());
        verify(mediaRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate_Success() {
        MediaEntity mediaEntity = new MediaEntity();
        Media media = new Media();

        when(mediaRepository.findById(1L)).thenReturn(Optional.of(mediaEntity));
        when(mediaRepository.save(mediaEntity)).thenReturn(mediaEntity);
        when(mediaMapper.fromEntityToModel(mediaEntity)).thenReturn(media);

        Media result = mediaRepositoryAdapter.update(1L, media);

        assertNotNull(result);
        verify(mediaRepository, times(1)).findById(1L);
        verify(mediaRepository, times(1)).save(mediaEntity);
    }

    @Test
    public void testUpdate_NotFound() {
        when(mediaRepository.findById(1L)).thenReturn(Optional.empty());

        Media result = mediaRepositoryAdapter.update(1L, new Media());

        assertNull(result);
        verify(mediaRepository, times(1)).findById(1L);
        verify(mediaRepository, never()).save(any(MediaEntity.class));
    }

    @Test
    public void testFindByName_Success() {
        MediaEntity mediaEntity = new MediaEntity();
        Media media = new Media();

        when(mediaRepository.findByName("test")).thenReturn(mediaEntity);
        when(mediaMapper.fromEntityToModel(mediaEntity)).thenReturn(media);

        Media result = mediaRepositoryAdapter.findByName("test");

        assertNotNull(result);
        verify(mediaRepository, times(1)).findByName("test");
    }

    @Test
    public void testFindByName_NotFound() {
        when(mediaRepository.findByName("test")).thenReturn(null);

        assertThrows(ResourceFoundException.class, () -> {
            mediaRepositoryAdapter.findByName("test");
        });

        verify(mediaRepository, times(1)).findByName("test");
    }

}
