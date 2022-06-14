package ru.itis.threedportalserver.repositories;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.models.ModelFile;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

public interface BucketFileRepository {

    List<S3Object> getS3Files();

    /**
     * Method to upload file to bucket
     * @param file
     * @param uploadFileName
     * @return Entity tag - MD5 checksum of uploaded object
     */
    String uploadFile(
            MultipartFile file,
            String uploadFileName,
            String username
    );

    String getPreSignedUrlFromModelFile(ModelFile modelFile);
    String getPreSignedUrlFromS3Object(S3Object s3Object);
}
