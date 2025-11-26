package com.api_gateway_service.filter;

import com.api_gateway_service.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JWTAuthenticationFilter implements GlobalFilter ,Ordered {


    @Autowired
    private JwtUtil jwtUtil ;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest() ;
        String path = request.getURI().getPath() ;
        System.out.println("Path ==> " + path);

        // 1. Allow unprotected paths (login, register)
        if (path.contains("/auth/")) {
            return chain.filter(exchange);
        }

        // 2. Extract authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        // 3. Validate token
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4. Extract claims
        String userId = jwtUtil.extractUserId(token);
        String roles = jwtUtil.extractRoles(token);


        System.out.println("Roles ==> " +roles);

        // 5. Add identity headers for downstream services
        ServerHttpRequest modifiedReq = request.mutate()
                .header("X-User-Id", userId)
                .header("X-Roles", roles)
                .build();

        return chain.filter(exchange.mutate().request(modifiedReq).build());

    }



    //SETS THIS FILTER AS HIGHEST PRIORITY
    @Override
    public int getOrder() {
        return -1;
    }
}
