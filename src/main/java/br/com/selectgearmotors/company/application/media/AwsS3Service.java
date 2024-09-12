package br.com.selectgearmotors.company.application.media;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Autowired
    public AwsS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String generatePreSignedUrl(String filePath,
                                       String bucketName,
                                       HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
        return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
    }

    public void uploadToS3(String bucketName, String fileName, InputStream inputStream , ObjectMetadata metadata) {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, metadata);

        request.setStorageClass(StorageClass.StandardInfrequentAccess);

        /* Set Canned ACL as BucketOwnerFullControl */
        request.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
        PutObjectResult putObjectResult = amazonS3.putObject(request);
        log.info("PutObjectResult: " + putObjectResult);
    }

    public AmazonS3 getAmazonS3() {
        return amazonS3;
    }
}
