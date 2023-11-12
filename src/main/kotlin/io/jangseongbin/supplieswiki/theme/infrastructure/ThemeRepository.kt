package io.jangseongbin.supplieswiki.theme.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface ThemeRepository : JpaRepository<Theme, Long> {
}
