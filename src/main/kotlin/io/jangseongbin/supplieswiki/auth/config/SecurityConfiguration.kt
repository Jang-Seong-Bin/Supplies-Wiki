package io.jangseongbin.supplieswiki.auth.config

import io.jangseongbin.supplieswiki.auth.filter.JwtFilter
import io.jangseongbin.supplieswiki.auth.handler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtConfiguration: JwtConfiguration,

    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .httpBasic { it.disable() }
        .exceptionHandling {
            it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        }
        .authorizeHttpRequests { authorizeRequests ->
            authorizeRequests
                .requestMatchers(
                    AntPathRequestMatcher("/swagger-ui/**"),
                    AntPathRequestMatcher("/v3/**"),
                    AntPathRequestMatcher("/api/v1/theme", GET.name()),
                    AntPathRequestMatcher("/api/v1/theme/**", GET.name()),
                    AntPathRequestMatcher("/api/v1/auth/login"),
                ).permitAll()
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .addFilterBefore(
            JwtFilter(jwtConfiguration),
            UsernamePasswordAuthenticationFilter::class.java,
        )
        .build()!!

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
