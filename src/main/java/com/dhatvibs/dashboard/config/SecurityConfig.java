package com.dhatvibs.dashboard.config;

import com.dhatvibs.dashboard.security.JwtAuthFilter;
import com.dhatvibs.dashboard.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

            	    // PUBLIC
            	    .requestMatchers(
            	            "/auth/**",
            	            "/v3/api-docs/**",
            	            "/swagger-ui/**",
            	            "/swagger-ui.html"
            	    ).permitAll()

            	    // DASHBOARD VIEW (Manager allowed)
            	    .requestMatchers(HttpMethod.GET, "/dashboards/**")
            	    .hasAnyRole("ADMIN","DATA_ANALYST","MANAGER")

            	    // DASHBOARD CREATE / UPDATE / DELETE (Admin only)
            	    .requestMatchers(HttpMethod.POST, "/dashboards/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers(HttpMethod.PUT, "/dashboards/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers(HttpMethod.DELETE, "/dashboards/**")
            	    .hasRole("ADMIN")

            	    // ANALYTICS
            	    .requestMatchers("/api/analytics/**")
            	    .hasAnyRole("ADMIN","DATA_ANALYST","MANAGER")

            	    // DATA UPLOAD
            	    .requestMatchers("/api/files/**")
            	    .hasAnyRole("ADMIN","DATA_ANALYST")

            	    // DATASETS
            	    .requestMatchers("/api/datasets/**")
            	    .hasAnyRole("ADMIN","DATA_ANALYST")

            	    // DATASET RECORDS
            	    .requestMatchers("/api/dataset-records/**")
            	    .hasAnyRole("ADMIN","DATA_ANALYST")

            	    .anyRequest().authenticated()
            	)

            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}