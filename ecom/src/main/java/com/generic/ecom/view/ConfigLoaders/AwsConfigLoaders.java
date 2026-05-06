package com.generic.ecom.view.ConfigLoaders;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties(prefix = "aws")
public record AwsConfigLoaders(
        String region,
        String accessKey,
        String secretKey,
        S3Properties s3
) {
    public record S3Properties(
            String endpoint,
            int preSignedUrlExpirationInSeconds, // in seconds
            Map<String, String> buckets // Handles infinite buckets dynamically
    ) {}
}
