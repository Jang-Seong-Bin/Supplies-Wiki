package io.jangseongbin.supplieswiki.theme.data

import org.springframework.data.jpa.repository.JpaRepository

interface ThemeRepository : JpaRepository<Theme, Long> {
}
