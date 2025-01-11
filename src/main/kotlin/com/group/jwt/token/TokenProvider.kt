package com.group.jwt.token

import com.group.jwt.auth.model.User
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey
import kotlin.time.Duration
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenProvider(
    private val tokenProperties: TokenProperties
) {
    private val logger = LoggerFactory.getLogger(TokenProvider::class.java)

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(tokenProperties.secretKey.toByteArray())
    }

    fun generateToken(user: User, expiredAt: Duration): String {
        val now = Date()
        val expiration = Date(now.time + expiredAt.inWholeMilliseconds)

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(tokenProperties.issuer)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .setSubject(user.email)
            .claim("id", user.id)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            logger.error("Invalid token: ${e.message}")
            false
        }
    }
}
