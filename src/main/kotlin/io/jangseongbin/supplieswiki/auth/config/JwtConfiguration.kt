package io.jangseongbin.supplieswiki.auth.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import io.jangseongbin.supplieswiki.auth.jwt.Claims
import io.jangseongbin.supplieswiki.auth.jwt.Token
import java.util.Date
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
class JwtConfiguration(
    val header: String,

    private val issuer: String,

    secret: String,

    accessTokenExpireMinute: Int,

    refreshTokenExpireMinute: Int,
) {
    private val algorithm = HMAC512(secret)

    private val accessTokenExpireSecond = accessTokenExpireMinute * 60 * 1000

    private val refreshTokenExpireSecond = refreshTokenExpireMinute * 60 * 1000

    fun refreshToken(token: String): Token {
        return with(verify(token)) { createToken(this) }
    }

    fun verify(token: String): Claims {
        val verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()

        return Claims(verifier.verify(token))
    }

    fun createToken(claims: Claims): Token {
        return with(Date()) {
            val accessToken = createJwt(claims, this, accessTokenExpireSecond)
            val refreshToken = createJwt(claims, this, refreshTokenExpireSecond)

            Token(accessToken, refreshToken)
        }
    }

    private fun createJwt(claims: Claims, date: Date, tokenExpireTime: Int): String {
        return JWT.create().apply {
            withClaim(USER_ID, claims.userId)
            withClaim(ROLE, claims.role)
            withIssuer(issuer)
            withIssuedAt(date)
            withExpiresAt(Date(date.time + tokenExpireTime))
        }.sign(algorithm)
    }

    companion object {
        private const val USER_ID = "userId"

        private const val ROLE = "role"
    }
}
