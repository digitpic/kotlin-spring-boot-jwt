package com.group.jwt.auth.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime,
) {
    constructor() : this(0, "", "", LocalDateTime.now(), LocalDateTime.now())
}
