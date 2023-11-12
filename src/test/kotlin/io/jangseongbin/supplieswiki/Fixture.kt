package io.jangseongbin.supplieswiki

import io.jangseongbin.supplieswiki.theme.infrastructure.Category
import io.jangseongbin.supplieswiki.theme.infrastructure.Content
import io.jangseongbin.supplieswiki.user.domain.User
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import java.time.LocalDateTime

object Fixture {
    private val createdAt = Arb.localDateTime(minYear = 2020, maxYear = 2022).single()

    fun user(updated: Boolean) = User(
        id = Arb.long(min = 1).single(),
        nickname = Arb.string(5..20).single(),
        loginId = Arb.string(3..10).single() + Arb.long(range = 1L .. 10000).single(),
        password = Arb.stringPattern("([a-zA-Z0-9]{8,20})").single(),
        createdAt = createdAt,
        updatedAt = if (updated) Arb.localDateTime(minLocalDateTime = createdAt).single() else null,
    )

    fun category() = Category(
        name = Arb.stringPattern("([a-zA-Z]{3,20})").single(),
        createdAt = LocalDateTime.now(),
        updatedAt = null
    )

    fun content(userId: Long) = Content(
        userId = userId,
        contents = Arb.string(100..500).single(),
        createdAt = LocalDateTime.now()
    )
}
