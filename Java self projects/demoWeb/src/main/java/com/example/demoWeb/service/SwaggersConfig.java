package com.example.demoWeb.service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggersConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo Web API")
                        .description("Self project REST APIs documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nawaf")
                                .url("https://github.com/nawafalsmail")
                                .email("nn@gmail.com"))
                        .termsOfService("Free to use")
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("API License")
                                .url("https://github.com/nawafalsmail"))
                );
    }
}