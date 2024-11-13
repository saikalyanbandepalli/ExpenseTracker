package com.microservice.comment_reaction_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allows all paths
                .allowedOrigins("*") // Allows all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify HTTP methods as needed
                .allowedHeaders("*") // Allows all headers
                .allowCredentials(false); // Allows cookies, if needed
    }
}
