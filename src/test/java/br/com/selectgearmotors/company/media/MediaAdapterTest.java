package br.com.selectgearmotors.company.media;

import br.com.selectgearmotors.company.application.database.repository.MediaRepositoryAdapter;
import br.com.selectgearmotors.company.application.media.AwsS3Service;
import br.com.selectgearmotors.company.application.media.MediaAdapter;
import br.com.selectgearmotors.company.application.media.dto.FileInfo;
import br.com.selectgearmotors.company.core.domain.Media;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaAdapterTest {

    @Mock
    private MediaRepositoryAdapter mediaRepositoryAdapter;

    @Mock
    private AwsS3Service awsS3Service;

    @InjectMocks
    private MediaAdapter mediaAdapter;

    @BeforeEach
    public void setUp() {
        mediaAdapter = new MediaAdapter(mediaRepositoryAdapter, awsS3Service);
    }

    @Test
    public void testInit() {
        mediaAdapter.init();
        assertTrue(Files.exists(mediaAdapter.getRoot()));
    }

    @Test
    public void testSave() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));

        mediaAdapter.save(multipartFile);

        verify(awsS3Service, times(1)).uploadToS3(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));
        verify(mediaRepositoryAdapter, times(1)).save(any(Media.class));
    }

    @Disabled
    public void testSaveFileAlreadyExists() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
        doThrow(FileAlreadyExistsException.class).when(awsS3Service).uploadToS3(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));

        assertThrows(RuntimeException.class, () -> mediaAdapter.save(multipartFile));
    }

    @Disabled
    public void testGetMediaFromS3() {
        Media media = Media.builder().name("test.png").build();
        when(mediaRepositoryAdapter.findById(anyLong())).thenReturn(media);

        FileInfo fileInfo = mediaAdapter.getMediaFromS3(1L);

        assertNotNull(fileInfo);
        assertEquals("test.png", fileInfo.getName());
    }

    @Disabled
    public void testLoad() {
        Resource resource = mediaAdapter.load("test.png");
        assertNotNull(resource);
    }

    @Test
    public void testDeleteAll() {
        mediaAdapter.deleteAll();
        assertFalse(Files.exists(mediaAdapter.getRoot()));
    }

    @Test
    public void testLoadAll() {
        Stream<Path> paths = mediaAdapter.loadAll();
        assertNotNull(paths);
    }

    @Test
    public void testFindAll() {
        List<Media> mediaList = mediaAdapter.findAll();
        assertNotNull(mediaList);
    }
}