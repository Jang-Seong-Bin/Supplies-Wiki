package io.jangseongbin.supplieswiki.auth.jwt

import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

class Claims(
    val userId: Long,

    val role: String,

    val issuedAt: Date? = null,

    val expiresAt: Date? = null,
) {
    constructor(decodedJWT: DecodedJWT) : this(
        userId = decodedJWT.getClaim(USER_ID).asLong(),
        role = Role.from(decodedJWT.getClaim(ROLE).asString()).role,
        issuedAt = decodedJWT.issuedAt,
        expiresAt = decodedJWT.expiresAt,
    )

    companion object {
        private const val USER_ID = "userId"

        private const val ROLE = "role"
    }
}
