package com.example.demoSecEx.Security;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.web.servlet.function.RequestPredicates;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nawaf's APIs",
                        email = "nawaf@gmail.com",
                        url = "https://www.google.com/"
                ),
                description = "OpenApi implemented from spring boot",
                title = "OpenApi Documentation - Nawaf",
                version = "1.0.0",
                license = @License(
                        name = "Licence name",
                        url = "http://some-url"
                ), termsOfService = "Terms of Service"
        ), servers = {

                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                    ),
                @Server(
                        description = "DEV ENV",
                        url = "https://www.google.com/"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
//this class is the configuration for the swagger security openApi
public class OpenApiConfig {
}

