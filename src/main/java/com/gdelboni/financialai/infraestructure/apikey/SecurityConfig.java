package com.gdelboni.financialai.infraestructure.apikey;

import com.gdelboni.financialai.infraestructure.ratelimit.FixedWindowRateLimiter;
import com.gdelboni.financialai.infraestructure.ratelimit.RateLimitFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Clock;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            @Value("${app.security.api-key}") String apiKey,
                                            @Value("${app.rate-limit.general.requests-per-minute}") int generalLimit,
                                            @Value("${app.rate-limit.ai.requests-per-minute}") int aiLimit) throws Exception {
        if (apiKey == null || apiKey.length() < 32)
            throw new IllegalStateException("APP_API_KEY must have at least 32 characters");

        var clock = Clock.systemUTC();
        var rateLimitFilter = new RateLimitFilter(
                new FixedWindowRateLimiter(generalLimit, 60_000L, clock),
                new FixedWindowRateLimiter(aiLimit, 60_000L, clock));

        http.csrf(c -> c.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(b -> b.disable()).formLogin(f -> f.disable()).logout(l -> l.disable())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new ApiKeyAuthFilter(apiKey), RateLimitFilter.class);
        return http.build();
    }
}