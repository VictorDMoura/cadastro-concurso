package com.concurso.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final long GENERIC_LIMIT = 60;
    private static final long SALVAR_LIMIT = 10;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Bucket bucket = resolveBucket(request);
        if (bucket.tryConsume(1)) {
            return true;
        }
        response.setStatus(429);
        return false;
    }

    private Bucket resolveBucket(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        boolean isSalvar = "POST".equalsIgnoreCase(request.getMethod()) && "/salvar".equals(request.getRequestURI());
        String key = ip + (isSalvar ? ":POST_SALVAR" : ":GENERIC");
        long capacity = isSalvar ? SALVAR_LIMIT : GENERIC_LIMIT;

        return buckets.computeIfAbsent(key, k -> {
            Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, WINDOW));
            return Bucket.builder().addLimit(limit).build();
        });
    }
}
