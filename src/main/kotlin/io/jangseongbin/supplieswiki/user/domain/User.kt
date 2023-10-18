package io.jangseongbin.supplieswiki.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class User(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,

    val loginId: String,

    val nickname: String,

    var password: String,

    val createdAt: LocalDateTime,

    var updatedAt: LocalDateTime,
)
