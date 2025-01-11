package com.group.jwt.auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    val id: Long,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
