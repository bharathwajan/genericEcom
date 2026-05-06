package com.generic.ecom.view.ConfigClasses;

import com.generic.ecom.view.ConfigLoaders.AwsConfigLoaders;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(AwsConfigLoaders.class)
public class AWSConfig {
    private final AwsConfigLoaders awsConfig;

    public AWSConfig(AwsConfigLoaders awsConfig) {
        this.awsConfig = awsConfig;
    }

    @Bean
    public S3Client s3Client(AwsConfigLoaders awsConfig) {
        return S3Client.builder()
                .endpointOverride(URI.create(awsConfig.s3().endpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsConfig.accessKey(),
                                awsConfig.secretKey()
                        )
                ))
                .region(Region.of(awsConfig.region()))
                .forcePathStyle(true) // Required for MinIO routing
                .build();
    }


    @Bean
    public S3Presigner s3Presigner(AwsConfigLoaders awsConfig) {

        // 1. Create the Path Style configuration
        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(true) // THIS IS THE MAGIC FIX!
                .build();

        // 2. Pass it to the Presigner builder
        return S3Presigner.builder()
                .endpointOverride(URI.create(awsConfig.s3().endpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsConfig.accessKey(),
                                awsConfig.secretKey()
                        )
                ))
                .region(Region.of(awsConfig.region()))
                .serviceConfiguration(s3Configuration) // Apply it here
                .build();
    }

}