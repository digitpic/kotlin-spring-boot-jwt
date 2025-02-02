package com.group.jwt.auth.util

import com.group.jwt.auth.domain.Member
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import org.springframework.beans.factory.annotation.Value

class TokenProvider(
    @Value("\${spring.jwt.issuer}")
    private val issuer: String,

    @Value("\${spring.jwt.secret_key}")
    private val secretKey: String
) {
    fun generate(member: Member, expiry: Date): String {
        val now: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .add("alg", "HS512")
            .and()
            .issuer(issuer)
            .issuedAt(now)
            .notBefore(now)
            .expiration(expiry)
            .subject(member.username)
            .claim("id", member.getId())
            .claim("nickname", member.getName())
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), Jwts.SIG.HS512)
            .compact()
    }

    fun validate(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parse(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
