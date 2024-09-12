package br.com.selectgearmotors.company.application.media;

import br.com.selectgearmotors.company.application.database.repository.MediaRepositoryAdapter;
import br.com.selectgearmotors.company.application.media.dto.FileInfo;
import br.com.selectgearmotors.company.core.domain.Media;
import br.com.selectgearmotors.company.infrastructure.entity.domain.MediaType;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MediaAdapter {

    public static final String UPLOAD_PATH_DIRECTORY = "src/main/resources/uploads";
    public static final String WAGON_DRIVER_IMAGES_BUCKET_S3 = "wagon-driver-images";
    private final MediaRepositoryAdapter mediaRepositoryAdapter;
    private final AwsS3Service awsS3Service;

    public void init() {
        try {
            Files.createDirectories(getRoot());
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            log.info("URL S3: " + originalFilename);
            String urlS3 = awsS3Service.generatePreSignedUrl(originalFilename, WAGON_DRIVER_IMAGES_BUCKET_S3, HttpMethod.PUT);
            log.info("URL S3: " + urlS3);
            InputStream inputStream = multipartFile.getInputStream();
            //Files.copy(inputStream, this.root.resolve(originalFilename));

            File file = new File(UPLOAD_PATH_DIRECTORY + "/" + originalFilename); //"src/main/resources/targetFile.tmp");
            String objectKey = file.getName();
            //file.transferTo(file1);

            /* Create and Set Object Metadata */
            ObjectMetadata metadata = new ObjectMetadata();
            var map = new HashMap<String, String>();
            map.put("ImageType", "PNG");

            metadata.setUserMetadata(map);

            /* Set Content Length */
            metadata.setContentLength(file.length());
            awsS3Service.uploadToS3(WAGON_DRIVER_IMAGES_BUCKET_S3, objectKey, inputStream, metadata);

            String s3Url = "https://" + WAGON_DRIVER_IMAGES_BUCKET_S3 + ".s3.amazonaws.com/" + originalFilename;
            Media media = Media.builder()
                    .name(originalFilename)
                    .mediaType(MediaType.PNG)
                    .path(s3Url)
                    .build();

            mediaRepositoryAdapter.save(media);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public FileInfo getMediaFromS3(Long mediaId) {
        S3ObjectInputStream inputStream = null;
        FileOutputStream fos = null;

        try {
            /* First way to Get Object from Amazon S3 */

            /* Send Get Object Bucket Request */
            Media resultMedia = mediaRepositoryAdapter.findById(mediaId);
            if (resultMedia != null) {
                String mediaFileName = resultMedia.getName();
                S3Object object = awsS3Service.getAmazonS3().getObject(WAGON_DRIVER_IMAGES_BUCKET_S3, mediaFileName);
                log.info("S3 Object: " + object.getKey());

                GetObjectRequest request = new GetObjectRequest(WAGON_DRIVER_IMAGES_BUCKET_S3, mediaFileName);
                log.info("Request: " + request.getKey());
                /* Send Get Object Bucket Request &amp;amp;amp; Save it to File 's4.png' */
                ObjectMetadata metadata = awsS3Service.getAmazonS3().getObject(request, new File(mediaFileName));

                log.info("Last Modified Date" + metadata.getLastModified());

                String s3Url = "https://" + WAGON_DRIVER_IMAGES_BUCKET_S3 + ".s3.amazonaws.com/" + mediaFileName;

                return FileInfo.builder()
                        .name(mediaFileName)
                        .url(s3Url)
                        .build();
            }
        } catch (AmazonServiceException e) {

            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

     public Resource load(String filename) {
        try {
            Path file = getRoot().resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(getRoot().toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.getRoot(), 1).filter(path -> !path.equals(this.getRoot())).map(this.getRoot()::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public List<Media> findAll() {
        return mediaRepositoryAdapter.findAll();
    }

    public Path getRoot() {
        return Paths.get(UPLOAD_PATH_DIRECTORY);
    }
}
