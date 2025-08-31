package com.example.cart_service.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI cartOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Cart Service API")
                .version("v1")
                .description("APIs for managing user carts and cart items"));
    }
}

