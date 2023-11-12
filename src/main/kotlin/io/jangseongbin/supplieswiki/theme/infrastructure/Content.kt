package io.jangseongbin.supplieswiki.theme.infrastructure

import jakarta.persistence.Embeddable
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import org.springframework.data.annotation.CreatedDate

@Embeddable
class Content(
    val userId: Long,

    val contents: String,

    @CreatedDate
    val createdAt: LocalDateTime = now(),
)
