package com.telstra.billing_system.config;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public AmazonS3 s3client() {
        return AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1") 
                .build();
    }
}
