package com.example.gateway_service.filter;

import com.example.gateway_service.config.RouteValidator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {

    private final WebClient.Builder webClientBuilder;
    private final RouteValidator routeValidator;

    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder, RouteValidator routeValidator) {
        this.webClientBuilder = webClientBuilder;
        this.routeValidator = routeValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();

        if (!routeValidator.isSecured.test(request)) {
            // Skip JWT check for public endpoints
            return chain.filter(exchange);
        }
//        // Skip authentication for login/signup
//        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/signup")) {
//            return chain.filter(exchange);
//        }

        // Check Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // Call Auth Service to validate token
        return webClientBuilder.build()
                .get()
                .uri("http://auth-service/api/auth/validate?token=" + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new RuntimeException("Token validation failed"))
                )
                .bodyToMono(Boolean.class)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
                    }
                    return chain.filter(exchange);
                });
    }

    // Helper method for sending error responses
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // Priority
    }
}
