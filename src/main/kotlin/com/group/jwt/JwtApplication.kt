package com.group.jwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class JwtApplication

fun main(args: Array<String>) {
    runApplication<JwtApplication>(*args)
}
