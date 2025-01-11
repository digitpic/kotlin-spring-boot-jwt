package com.group.jwt.token

import com.group.jwt.auth.model.Member
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import kotlin.time.Duration
import org.springframework.stereotype.Service

@Service
class TokenProvider(
    private val tokenProperties: TokenProperties
) {
    fun generateToken(member: Member, expiredAt: Duration): String {
        val now = Date()
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(now)
            .claim("id", member.id)
            .signWith(SecretKeySpec(tokenProperties.accessSecretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
            .compact()
    }

    fun validToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(tokenProperties.accessSecretKey).build()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
