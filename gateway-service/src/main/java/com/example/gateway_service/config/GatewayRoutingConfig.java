package com.example.gateway_service.config;

import com.example.gateway_service.filter.JwtAuthenticationFilter;
import com.example.gateway_service.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    private final RouteValidator validator;
    private final JWTUtil jwtUtil;

    @Autowired
    public GatewayRoutingConfig(RouteValidator validator, JWTUtil jwtUtil) {
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //Auth-service (Login/Register - No token required)
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(new JwtAuthenticationFilter(validator, jwtUtil)
                                .apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://auth-service"))
                .build();
    }
}
