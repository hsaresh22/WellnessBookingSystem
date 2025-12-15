package com.hs.WellnessBookingSystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger configuration class to set up OpenAPI documentation
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wellnessOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wellness Booking System API")
                        .description("API documentation for Wellness Booking System")
                        .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repo")
                        .url("https://github.com/your-repo-link"));
    }
}
