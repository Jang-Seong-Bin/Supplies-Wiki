package io.jangseongbin.supplieswiki.theme.request

import io.jangseongbin.supplieswiki.theme.infrastructure.Content

class UpdateThemeRequest(
    val themeId: Long,

    val userId: Long,

    val contents: String,
) {
    fun toUpdateTheme(): UpdateTheme {
        return UpdateTheme(
            themeId = themeId,
            content = Content(
                userId = userId,
                contents = contents,
            ),
        )
    }
}
