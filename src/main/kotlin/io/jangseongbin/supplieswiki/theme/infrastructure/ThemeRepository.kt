package io.jangseongbin.supplieswiki.theme.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ThemeRepository : JpaRepository<Theme, Long> {
    fun findAllByName(name: String): List<Theme>
}
