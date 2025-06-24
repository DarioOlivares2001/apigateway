package com.gateway.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(ex -> ex

                    // ---------- PRE-FLIGHT ----------
                    .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // ---------- WEBSOCKET ----------
                    .pathMatchers(HttpMethod.GET, "/ws/**").permitAll()

                    // ---------- ENDPOINTS PÚBLICOS ----------
                    .pathMatchers(HttpMethod.POST,
                        "/api/usuarios/register",
                        "/api/usuarios/login",
                        "/api/usuarios/confirm"
                    ).permitAll()

                    .pathMatchers(HttpMethod.GET,
                        "/api/jobs/**",
                        "/api/match/**",
                        "/api/postulaciones/**",
                        "/api/usuario-habilidades/**",      // ← habilidades públicas
                        "/api/messages/**",                          // ← AGREGADO
                        "/messages/unread/count/**",                 // ← AGREGADO
                        "/messages/unread/by-sender/**"   
                    ).permitAll()

                    .pathMatchers(HttpMethod.PUT,
                        "/messages/*/*/seen"
                    ).permitAll()

                    // ---------- TODO LO DEMÁS REQUIERE JWT ----------
                    .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
       cfg.setAllowedOrigins(List.of("http://localhost:4200","https://matchwork.vercel.app","https://matchwork-three.vercel.app"));
       cfg.addAllowedOriginPattern("https://*.vercel.app");
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
