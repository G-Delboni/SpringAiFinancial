package com.gdelboni.financialai.infraestructure.ratelimit;

import com.gdelboni.financialai.infraestructure.ratelimit.FixedWindowRateLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

public class RateLimitFilter extends OncePerRequestFilter {
    private static final Set<String> AI_PATHS =
            Set.of("/transaction/ai", "/api/transcribe", "/api/chat", "/chatmodel");

    private final FixedWindowRateLimiter generalLimiter;
    private final FixedWindowRateLimiter aiLimiter;

    public RateLimitFilter(FixedWindowRateLimiter generalLimiter, FixedWindowRateLimiter aiLimiter) {
        this.generalLimiter = generalLimiter; this.aiLimiter = aiLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        var limiter = AI_PATHS.contains(req.getServletPath()) ? aiLimiter : generalLimiter;
        var decision = limiter.tryAcquire(req.getRemoteAddr());
        if (decision.allowed()) { chain.doFilter(req, res); return; }

        res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        res.setHeader("Retry-After", String.valueOf(decision.retryAfterSeconds()));
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write("{\"error\":\"rate_limit_exceeded\"}");
    }
}