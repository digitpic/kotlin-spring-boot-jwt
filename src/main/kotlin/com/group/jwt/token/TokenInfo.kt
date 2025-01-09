package com.group.jwt.token

class TokenInfo (
    val userId: Long,
    val prefix: String,
    val accessToken: String,
    val refreshToken: String,
)
