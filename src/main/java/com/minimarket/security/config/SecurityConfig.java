package com.minimarket.security.config;

import com.minimarket.security.JwtAuthenticationFilter;
import com.minimarket.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwt;
    private final CustomUserDetailsService users;

    public SecurityConfig(
            JwtAuthenticationFilter jwt,
            CustomUserDetailsService users
    ) {
        this.jwt = jwt;
        this.users = users;
    }

    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin())
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Endpoints publicos
                        .requestMatchers(
                                "/api/auth/**",
                                "/public/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()

                        // Consultar productos y categorias
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).hasAnyRole(
                                "CLIENTE",
                                "CAJERO",
                                "ADMIN"
                        )

                        // Crear productos y categorias
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).hasRole("ADMIN")

                        // Actualizar productos y categorias
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).hasRole("ADMIN")

                        // Eliminar productos y categorias
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/productos/**",
                                "/api/categorias/**"
                        ).hasRole("ADMIN")

                        // Ventas
                        .requestMatchers("/api/ventas/**")
                        .hasAnyRole("CAJERO", "ADMIN")

                        // Inventario y usuarios
                        .requestMatchers(
                                "/api/inventario/**",
                                "/api/usuarios/**"
                        ).hasRole("ADMIN")

                        // Cualquier otro endpoint requiere autenticacion
                        .anyRequest().authenticated()
                )

                .authenticationProvider(provider())

                .addFilterBefore(
                        jwt,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(users);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
