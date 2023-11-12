package io.jangseongbin.supplieswiki.theme.infrastructure

import jakarta.persistence.Embeddable
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Embeddable
class Category(
    val name: String,

    @CreatedDate
    val createdAt: LocalDateTime = now(),

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null,
)
