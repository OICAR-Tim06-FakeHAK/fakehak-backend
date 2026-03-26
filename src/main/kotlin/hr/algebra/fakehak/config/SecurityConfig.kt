package hr.algebra.fakehak.config

import hr.algebra.fakehak.security.JwtAuthenticationFilter
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    @Order(1)
    fun h2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher(PathRequest.toH2Console())
            .csrf { it.disable() }
            .headers { headers -> headers.frameOptions { it.sameOrigin() } }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
        return http.build()
    }

    @Bean
    @Order(2)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    // Public - no auth required
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()

                    // Case creation and location updates - USER (mobile) + employees
                    .requestMatchers(HttpMethod.POST, "/api/cases").hasAnyRole("USER", "ADMIN", "DISPATCHER")
                    .requestMatchers(HttpMethod.PATCH, "/api/cases/*/location").hasAnyRole("USER", "ADMIN", "DISPATCHER")
                    .requestMatchers(HttpMethod.GET, "/api/cases/user/*").hasAnyRole("USER", "ADMIN", "DISPATCHER")

                    // User profile and vehicles - USER + ADMIN
                    .requestMatchers(HttpMethod.GET, "/api/users/*").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/users/*").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/api/users/*/vehicles/**").hasAnyRole("USER", "ADMIN")

                    // Employee management - ADMIN only
                    .requestMatchers("/api/employees/**").hasRole("ADMIN")

                    // Case management & notes - DISPATCHER and ADMIN
                    .requestMatchers("/api/cases/**").hasAnyRole("ADMIN", "DISPATCHER")

                    // Everything else requires authentication
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}