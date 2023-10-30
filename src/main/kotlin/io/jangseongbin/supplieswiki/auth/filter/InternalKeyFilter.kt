package io.jangseongbin.supplieswiki.auth.filter

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter

class InternalKeyFilter(
    internalKeyManager: InternalKeyManager,
) : AbstractPreAuthenticatedProcessingFilter() {
    init {
        setAuthenticationManager(internalKeyManager)
    }

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): String? = request.getHeader(INTERNAL_KEY)

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest) = "N/A"

    companion object {
        private const val INTERNAL_KEY = "INTERNAL-KEY"
    }
}
