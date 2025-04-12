package com.mired.mired.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI miredOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MiRed API")
                        .description("Documentación de la API para la red social interna MiRed")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo MiRed")
                                .email("soporte@mired.com")
                                .url("https://mired.com")
                        )
                );
    }
}
