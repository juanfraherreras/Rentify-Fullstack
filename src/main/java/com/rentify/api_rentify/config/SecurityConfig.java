package com.rentify.api_rentify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitamos CORS con la nueva configuración
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitamos CSRF para permitir POST/PUT desde Flutter
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite peticiones de pre-vuelo (pre-flight)
                .requestMatchers("/api/**").permitAll() // Acceso libre a la API para desarrollo
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Cambio clave: permitimos cualquier origen para que tu celular Redmi pueda conectar
        configuration.setAllowedOriginPatterns(List.of("*")); 
        
        // Permitimos todos los métodos necesarios para el CRUD
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        
        // Permitimos todos los headers para evitar bloqueos
        configuration.setAllowedHeaders(List.of("*")); 
        
        // Importante para el manejo de sesiones si decides usarlas luego
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }
}