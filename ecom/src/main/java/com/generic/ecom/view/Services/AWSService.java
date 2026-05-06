package com.generic.ecom.view.Services;

import org.springframework.stereotype.Service;
import com.generic.ecom.view.ConfigLoaders.AwsConfigLoaders;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class AWSService {

    private final S3Presigner s3Presigner;
    private final AwsConfigLoaders awsConfig;

    public AWSService(S3Presigner s3Presigner, AwsConfigLoaders awsConfig) {
        this.s3Presigner = s3Presigner;
        this.awsConfig = awsConfig;
    }

    public String generateUploadUrl(String fileName) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(awsConfig.s3().buckets().get("product-images"))
                .key(fileName)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(awsConfig.s3().preSignedUrlExpirationInSeconds())) // URL expires in 15 mins
                .putObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    public String generateDownloadUrl(String fileName) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(awsConfig.s3().buckets().get("product-images"))
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1)) // View URL expires in 1 hour
                .getObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}