package io.jangseongbin.supplieswiki.theme.request

import io.jangseongbin.supplieswiki.theme.infrastructure.Content

class UpdateTheme(
    val themeId: Long,

    val content: Content,
)
