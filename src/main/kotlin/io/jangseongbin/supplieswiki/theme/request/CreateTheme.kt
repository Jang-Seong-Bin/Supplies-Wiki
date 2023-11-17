package io.jangseongbin.supplieswiki.theme.request

import io.jangseongbin.supplieswiki.theme.infrastructure.Category
import io.jangseongbin.supplieswiki.theme.infrastructure.Content

class CreateTheme(
    val name: String,

    val category: Category,

    val contents: List<Content>,
) {
    constructor(createThemeRequest: CreateThemeRequest) : this(
        name = createThemeRequest.name,
        category = createThemeRequest.category,
        contents = createThemeRequest.contents
            .map {
                content -> Content(userId = createThemeRequest.userId, contents = content)
            }
    )
}
