package com.group.jwt.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class TokenProperties {
    lateinit var accessSecretKey: String
    lateinit var refreshSecretKey: String
}
