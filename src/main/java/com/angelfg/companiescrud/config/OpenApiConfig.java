package com.angelfg.companiescrud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Companies CRUD",
        version = "1.0.0",
        description = "This is a CRUD for management companies"
    )
)
public class OpenApiConfig {
}
