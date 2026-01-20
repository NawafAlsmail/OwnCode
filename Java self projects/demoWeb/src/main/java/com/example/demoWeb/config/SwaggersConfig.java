package com.example.demoWeb.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "Internal Usage Env",
                        url = "http://localhost:${server.port}${server.servlet.context-path}"
                )
        }
)
@Configuration
public class SwaggersConfig implements WebMvcConfigurer {

    // Swagger UI header configuration
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger API Documentation")
                        .description("""
                               This documentation shows all the endpoints and its properties.
                               
                               This documentation and APIs are for internal use only
                               
                               Above at the " /v3/api-docs/public-apis " link, you will find\n
                               the documentation on JSON format with each endpoint's request method.
                               
                               
                               - All endpoints are JSON default format
                               - Example values shown for all endpoints
                               - Schemas endpoints using and its felids are at the bottom
                               - Use tags to navigate logical channels
                               """)
                        .version("1.0.0")
                        .license(new License()
                                .name("The UI Store")
                                .url("www.goggle.com"))
                )
                //this is for clickable extra links if needed
                .externalDocs(new ExternalDocumentation()
                        .description("Full API Guide & Tutorials")
                        .url(""));
    }
    // redirecting the Swagger UI url
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger", "/swagger-ui/index.html");
    }

    // Group by "public-apis", includes controllers annotated with @SwaggerVisibility
    @Bean
    public GroupedOpenApi publicApis() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .addOpenApiMethodFilter(this::isSwaggerVisible)
                .addOpenApiCustomizer(this::filterSchemasForPublicApis)
                .build();
    }

    // Check if method/class contains @SwaggerVisibility
    private boolean isSwaggerVisible(Method method) {
        // Check if the method itself has @SwaggerVisibility directly
        if (method.isAnnotationPresent(SwaggerVisibility.class)) {
            return true;
        }

        // Check annotations on the method for @SwaggerVisibility as meta-annotation (inner annotation)
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(SwaggerVisibility.class)) {
                return true;
            }
        }

        // Check on the class level
        return method.getDeclaringClass().isAnnotationPresent(SwaggerVisibility.class);
    }

    // Filters schemas based on the endpoints usage
    private void filterSchemasForPublicApis(OpenAPI openApi) {
        if (openApi.getPaths() == null || openApi.getComponents() == null ||
                openApi.getComponents().getSchemas() == null) return;


        // Collect all referenced schema names from requestBody and responses
        Set<String> referencedSchemas = openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .flatMap(operation -> {

                    // Request schema
                    Stream<String> requestSchemas = operation.getRequestBody() == null ||
                            operation.getRequestBody().getContent() == null ? Stream.empty() :
                            operation.getRequestBody().getContent().values().stream()
                                    .map(MediaType::getSchema)
                                    .filter(Objects::nonNull)
                                    .map(Schema::get$ref)
                                    .filter(Objects::nonNull)
                                    .map(ref -> ref.replace("#/components/schemas/", ""));

                    // Response schema
                    Stream<String> responseSchemas = operation.getResponses().values().stream()
                            .flatMap(apiResponse -> apiResponse.getContent() == null ? Stream.empty() :
                                    apiResponse.getContent().values().stream())
                            .flatMap(mediaType -> mediaType.getSchema() == null ? Stream.empty() :
                                    Stream.of(mediaType.getSchema().get$ref()))
                            .filter(Objects::nonNull)
                            .map(ref -> ref.replace("#/components/schemas/", ""));

                    return Stream.concat(requestSchemas, responseSchemas);

                })
                .collect(Collectors.toSet());

        // Keep only the schemas actually used by this "public-apis" group
        Map<String, Schema> schemas = openApi.getComponents().getSchemas();
        schemas.entrySet().removeIf(entry -> !referencedSchemas.contains(entry.getKey()));
    }
}
