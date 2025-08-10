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
        private final JwtAuthenticationFilter jwtAuthFilter; // ✅ Inject the bean

        @Autowired
        public GatewayRoutingConfig(RouteValidator validator, JWTUtil jwtUtil, JwtAuthenticationFilter jwtAuthFilter) {
            this.validator = validator;
            this.jwtUtil = jwtUtil;
            this.jwtAuthFilter = jwtAuthFilter;
        }

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
            return builder.routes()
                    //Auth-service (Login/Register - No token required)
                    .route("auth-service", r -> r.path("/api/auth/**")
                            .uri("lb://auth-service"))

                    //Product-Service (Product Service Call)
                    .route("product-service", r -> r.path("/api/products/**")
                            .filters(f ->
                                    f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config())))
                            .uri("lb://PRODUCT-SERVICE"))

                    .build();
        }
    }
