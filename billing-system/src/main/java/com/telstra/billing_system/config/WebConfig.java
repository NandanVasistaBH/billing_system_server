package com.telstra.billing_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Adjust as needed for production
                .allowedMethods("*")                     // Consider restricting methods if not all are needed
                .allowedHeaders("*")                     // Consider restricting headers if not all are needed
                .allowCredentials(true);
    }
}
