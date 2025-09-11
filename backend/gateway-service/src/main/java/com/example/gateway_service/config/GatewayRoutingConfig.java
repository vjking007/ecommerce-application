    package com.example.gateway_service.config;

    import com.example.gateway_service.filter.JwtAuthenticationFilter;
    import org.springframework.cloud.gateway.route.RouteLocator;
    import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class GatewayRoutingConfig {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthenticationFilter jwtAuthenticationFilter) {
            return builder.routes()
                    //Auth-service (Login/Register - No token required)
                    .route("auth-service", r -> r.path("/api/auth/**")
                            .uri("lb://auth-service"))

                    //Product-Service (Product Service Call)
                    .route("product-service", r -> r.path("/api/products/**")
                            .filters(f ->
                                    f.filter(jwtAuthenticationFilter))
                            .uri("lb://product-service"))

                    //Cart-Service (Cart Service Call)
                    .route("cart-service", r -> r.path("/api/cart/**")
                            .filters(f ->
                                    f.filter(jwtAuthenticationFilter))
                            .uri("lb://cart-service"))

                    .build();
        }
    }
