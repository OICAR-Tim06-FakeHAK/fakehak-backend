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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
            .headers { it.frameOptions { f -> f.sameOrigin() } }
            .authorizeHttpRequests { it.anyRequest().permitAll() }

        return http.build()
    }

    @Bean
    @Order(2)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {}
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            .authorizeHttpRequests { auth ->
                auth

                    // 🔥 IMPORTANT: CORS preflight
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // Public endpoints
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                    // Swagger
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()

                    // Cases (USER / ADMIN / DISPATCHER)
                    .requestMatchers(HttpMethod.POST, "/api/cases")
                    .hasAnyRole("USER", "ADMIN", "DISPATCHER")

                    .requestMatchers(HttpMethod.PATCH, "/api/cases/*/location")
                    .hasAnyRole("USER", "ADMIN", "DISPATCHER")

                    .requestMatchers(HttpMethod.GET, "/api/cases/user/*")
                    .hasAnyRole("USER", "ADMIN", "DISPATCHER")

                    // Users
                    .requestMatchers(HttpMethod.GET, "/api/users/*")
                    .hasAnyRole("USER", "ADMIN")

                    .requestMatchers(HttpMethod.PUT, "/api/users/*")
                    .hasAnyRole("USER", "ADMIN")

                    .requestMatchers("/api/users/*/vehicles/**")
                    .hasAnyRole("USER", "ADMIN")

                    // Employees
                    .requestMatchers("/api/employees/**")
                    .hasRole("ADMIN")

                    // Cases management
                    .requestMatchers("/api/cases/**")
                    .hasAnyRole("ADMIN", "DISPATCHER")

                    // fallback
                    .anyRequest().authenticated()
            }

            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowedOrigins = listOf("http://localhost:5173")
        config.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return source
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}