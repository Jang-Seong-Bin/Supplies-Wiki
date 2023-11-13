package io.jangseongbin.supplieswiki.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByLoginId(loginId: String): User?

    fun findByNickname(nickname: String): User?
}
