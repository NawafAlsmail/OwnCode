package com.example.demoWeb.config;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Denied. Invalid details provided",
                content = @Content(schema = @Schema(example = ""))
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Something went wrong. Error while calling client call",
                content = @Content(schema = @Schema(example = ""))
        )
})
public @interface SwaggerVisibility {}