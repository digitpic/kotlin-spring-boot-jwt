package com.group.jwt.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    val host: String,

    @Value("\${spring.data.redis.port}")
    val port: Int,
) {
    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(host, port)

        // Jedis, Lettuce 중 성능이 더 좋은 Lettuce 사용
        val lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ZERO)
            .shutdownTimeout(Duration.ZERO)
            .build()

        return LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.connectionFactory = lettuceConnectionFactory()

            // prefix 해시 값 나오지 않도록 serializer 설정 (ex. "\xac\xed\x00")
            this.keySerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
        }
    }

    @Bean
    fun cacheManager(): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(fromSerializer(StringRedisSerializer()))  // key serialize
            .serializeValuesWith(fromSerializer(GenericJackson2JsonRedisSerializer())) // value serialize
            .entryTtl(Duration.ofMinutes(2)) // 캐시 기본 TTL 2분 설정
            .disableCachingNullValues() // null data 캐싱 제외

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }
}
