package io.jangseongbin.supplieswiki.theme.request

class CreateThemeRequest(
    val userId: Long,

    val name: String,

    val category: String,

    val contents: List<String>,
)
