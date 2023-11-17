package io.jangseongbin.supplieswiki.theme.service

import io.jangseongbin.supplieswiki.theme.infrastructure.Theme
import io.jangseongbin.supplieswiki.theme.infrastructure.ThemeRepository
import io.jangseongbin.supplieswiki.theme.request.CreateTheme
import io.jangseongbin.supplieswiki.theme.request.UpdateTheme
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ThemeService(
    private val themeRepository: ThemeRepository,
) {
    @Transactional
    fun createTheme(request: CreateTheme): Theme {
        val theme = Theme(
            name = request.name,
            category = request.category,
            contents = request.contents,
        )

        return themeRepository.save(theme)
    }

    @Transactional
    fun updateTheme(request: UpdateTheme) {
        val theme =
            themeRepository.findByIdOrNull(request.themeId) ?: throw NoSuchElementException()

        themeRepository.save(theme.update(updateTheme = request))
    }
}
