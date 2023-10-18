package io.jangseongbin.supplieswiki.theme.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Embeddable
class Category(
    val name: String,

    @CreatedDate
    val createdAt: LocalDateTime,

    @LastModifiedDate
    val updatedAt: LocalDateTime,
)
