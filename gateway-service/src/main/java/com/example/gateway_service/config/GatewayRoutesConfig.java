package com.example.gateway_service.config;

import com.example.gateway_service.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayRoutesConfig {

    @Autowired
    private JwtAuthenticationFilter authFilter;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://USER-SERVICE"))

                .route("product-service", r -> r.path("/api/products/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://PRODUCT-SERVICE"))

                .route("order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://ORDER-SERVICE"))

                .route("inventory-service", r -> r.path("/api/inventory/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://INVENTORY-SERVICE"))

                .route("notification-service", r -> r.path("/api/notify/**")
                        .uri("lb://NOTIFICATION-SERVICE"))

                .build();
    }

}

