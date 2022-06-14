package ru.itis.threedportalserver.repositories;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.threedportalserver.config.VkCloudBucketAccountProperties;
import ru.itis.threedportalserver.config.VkCloudBucketProperties;
import ru.itis.threedportalserver.models.ModelFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BucketFileRepositoryImpl implements BucketFileRepository {

    private final VkCloudBucketProperties bucketProperties;
    private final VkCloudBucketAccountProperties bucketAccountProperties;

    private AwsBasicCredentials awsBasicCredentials;
    private S3Client s3Client;
    private S3Presigner s3Presigner;

    private void setUp() {
        if(awsBasicCredentials == null) {
            awsBasicCredentials = AwsBasicCredentials.create(
                    bucketAccountProperties.getAccessKey(),
                    bucketAccountProperties.getSecretKey()
            );
        }

        if(s3Client == null) {
            try {
                s3Client = S3Client.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                        .region(Region.of(bucketProperties.getRegion()))
                        .endpointOverride(new URI(bucketProperties.getHotboxUrl()))
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if(s3Presigner == null) {
            try {
                s3Presigner = S3Presigner.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                        .region(Region.of(bucketProperties.getRegion()))
                        .endpointOverride(new URI(bucketProperties.getHotboxUrl()))
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private List<S3Object> createListRequest(String prefix) {
        setUp();

        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .prefix(prefix)
                .bucket(bucketProperties.getName())
                .build();

        ListObjectsResponse res = s3Client.listObjects(listObjects);

        return res.contents();
    }

    @Override
    public List<S3Object> getS3Files() {
        return createListRequest(null);
    }

    @Override
    public String uploadFile(
            MultipartFile file,
            String uploadFileName,
            String username
    ) {
        setUp();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketProperties.getName())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(username + "/" + uploadFileName)
                .build();

        try {
            PutObjectResponse response = s3Client.putObject(
                    objectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );
            return response.eTag();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Incorrect file");
        }
    }

    @Override
    public String getPreSignedUrlFromModelFile(ModelFile modelFile) {
        setUp();

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketProperties.getName())
                        .key(
                                modelFile.getUserId().getEmail() +
                                        "/" +
                                        modelFile.getGeneratedName()
                        )
                        .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(8))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

    @Override
    public String getPreSignedUrlFromS3Object(S3Object s3Object) {
        setUp();

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketProperties.getName())
                        .key(s3Object.key())
                        .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(8))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

}
