package io.jangseongbin.supplieswiki.theme.domain

import jakarta.persistence.Embeddable
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate

@Embeddable
class Content(
    val userId: Long,

    val contents: String,

    @CreatedDate
    val createdAt: LocalDateTime,
)
