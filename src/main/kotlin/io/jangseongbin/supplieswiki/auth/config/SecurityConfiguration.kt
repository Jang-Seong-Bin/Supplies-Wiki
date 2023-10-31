package io.jangseongbin.supplieswiki.auth.config

import io.jangseongbin.supplieswiki.auth.filter.InternalKeyFilter
import io.jangseongbin.supplieswiki.auth.filter.InternalKeyManager
import io.jangseongbin.supplieswiki.auth.filter.JwtFilter
import io.jangseongbin.supplieswiki.auth.handler.JwtAccessDeniedHandler
import io.jangseongbin.supplieswiki.auth.handler.JwtAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
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

    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,

    @Value("\${internal.api.secret}")
    private val internalApiSecret: String,
) : WebSecurityConfiguration() {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .httpBasic { it.disable() }
        .exceptionHandling {
            it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            it.accessDeniedHandler(jwtAccessDeniedHandler)
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
            InternalKeyFilter(InternalKeyManager(internalApiSecret)),
            UsernamePasswordAuthenticationFilter::class.java,
        )
        .addFilterBefore(
            JwtFilter(jwtConfiguration),
            InternalKeyFilter::class.java,
        )
        .build()!!

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
