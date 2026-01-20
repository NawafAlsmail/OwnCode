package com.example.demoWeb.config;

import com.example.demoWeb.modle.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This generic Class is for swagger documentation
 * Each annotation contains the complete @Operation configuration for its endpoint
 */
@Configuration
public class GenericSwaggerDocs {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @SwaggerVisibility
    @Operation(
        summary = "Add Product",
        description = "Adding product to the schema",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Product.class))),
        responses = {
                @ApiResponse(responseCode = "200", description = "OK. Your request has been received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class )))
        }
    )
    public @interface AddProduct {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @SwaggerVisibility
    @Operation(
            summary = "Deleting product",
            description = "Deleting product from the schema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK. Your request has been received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class )))
            }
    )
    public @interface DeleteProduct {}
}