package com.recipe.app.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/recipes/favorites").hasAnyRole("BASIC", "PROFESSIONAL", "PREMIUM")
                    .requestMatchers(HttpMethod.GET, "/api/recipes/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/recipes/*/favorite").hasAnyRole("BASIC", "PROFESSIONAL", "PREMIUM")
                    .requestMatchers(HttpMethod.POST, "/api/recipes/*/review").hasAnyRole("PROFESSIONAL", "PREMIUM")
                    .requestMatchers(HttpMethod.POST, "/api/recipes/**").hasRole("PROFESSIONAL")
                    .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").hasRole("PROFESSIONAL")
                    .requestMatchers(HttpMethod.PUT, "/api/recipes/**").hasRole("PROFESSIONAL")
                    .anyRequest()
                    .authenticated())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
