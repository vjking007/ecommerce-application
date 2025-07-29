package com.example.gateway_service.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

//    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        String authHeader = req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }

        // Optionally validate the token here or delegate to Auth service

//        return webClientBuilder.build()
//                .post()
//                .uri("http://USER-SERVICE/api/auth/validate")
//                .header(HttpHeaders.AUTHORIZATION, authHeader)
//                .retrieve()
//                .bodyToMono(String.class)
//                .then(chain.filter(exchange)); // This is the only return now

        return  chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse res = exchange.getResponse();
        res.setStatusCode(status);
        return res.setComplete();
    }
}

