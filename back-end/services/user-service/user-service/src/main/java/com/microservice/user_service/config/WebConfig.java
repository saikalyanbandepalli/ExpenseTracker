package com.microservice.user_service.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all origins, or specify the allowed origins
        registry.addMapping("/**") // Allows all paths
                .allowedOrigins("http://localhost:3000") // Allows all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify HTTP methods as needed
                .allowedHeaders("*") // Allows all headers
                .allowCredentials(true); // Allows cookies, if needed
    }
}

