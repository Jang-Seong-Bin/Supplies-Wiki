package io.jangseongbin.supplieswiki

import io.jangseongbin.supplieswiki.theme.infrastructure.Category
import io.jangseongbin.supplieswiki.theme.infrastructure.Content
import io.jangseongbin.supplieswiki.theme.request.CreateThemeRequest
import io.jangseongbin.supplieswiki.theme.request.UpdateTheme
import io.jangseongbin.supplieswiki.user.data.Password
import io.jangseongbin.supplieswiki.user.data.User
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

object Fixture {
    private val createdAt = Arb.localDateTime(minYear = 2020, maxYear = 2022).single()

    fun user(updated: Boolean) = User(
        id = Arb.long(min = 1).single(),
        nickname = Arb.string(5..20).single(),
        loginId = Arb.string(3..10).single() + Arb.long(range = 1L .. 10000).single(),
        password = Password("jangseongbin123"),
        createdAt = createdAt,
        updatedAt = if (updated) Arb.localDateTime(minLocalDateTime = createdAt).single() else null,
    )

    fun updateRequestForNotExistingTheme() = UpdateTheme(
        themeId = -1,
        content = Content(
            userId = -1,
            contents = "hello",
        )
    )
}
