package io.jangseongbin.supplieswiki.auth.filter

import com.auth0.jwt.exceptions.TokenExpiredException
import io.jangseongbin.supplieswiki.auth.config.JwtConfiguration
import io.jangseongbin.supplieswiki.auth.jwt.AuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.net.URLDecoder
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE
import kotlin.text.Charsets.UTF_8
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtConfiguration: JwtConfiguration,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (SecurityContextHolder.getContext().authentication == null) {
            val token = request.getToken()

            token?.let {
                runCatching {
                    changeAuthentication(token, request)
                }.onFailure {
                    response.sendError(401, "token expired exception occurred")
                }
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun HttpServletRequest.getToken(): String? {
        return runCatching {
            val token = getHeader(jwtConfiguration.header).toString()
            val (scheme, credentials) = URLDecoder.decode(token, UTF_8).split(" ").let { it.first() to it.last() }

            credentials.takeIf { BEARER.matcher(scheme).matches() }
        }.getOrNull()
    }

    private fun changeAuthentication(token: String, httpServletRequest: HttpServletRequest) {
        runCatching { jwtConfiguration.verify(token) }
            .onSuccess { claims ->
                val principal = claims.userId
                val authorities = listOf(SimpleGrantedAuthority(claims.role))

                AuthenticationToken(principal = principal.toString(), authorities = authorities)
                    .apply { details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest) }
                    .also { authenticationToken ->
                        SecurityContextHolder.getContext().authentication = authenticationToken
                        SecurityContextHolder.getContext().authentication.isAuthenticated = true
                    }
            }.onFailure {
                if (it is TokenExpiredException) {
                    throw it
                }
            }
    }

    companion object {
        private val BEARER = Pattern.compile("^Bearer$", CASE_INSENSITIVE)
    }
}
