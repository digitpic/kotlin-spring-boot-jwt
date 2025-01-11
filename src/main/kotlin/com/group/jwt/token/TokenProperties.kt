package com.group.jwt.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class TokenProperties {
    lateinit var issuer: String
    lateinit var secretKey: String
}
