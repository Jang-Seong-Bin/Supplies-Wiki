package io.jangseongbin.supplieswiki.theme.request

import io.jangseongbin.supplieswiki.theme.infrastructure.Category

class CreateThemeRequest(
    val userId: Long,

    val name: String,

    val category: Category,

    val contents: List<String>,
)
