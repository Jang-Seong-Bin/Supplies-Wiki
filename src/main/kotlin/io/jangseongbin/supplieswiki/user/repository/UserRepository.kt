package io.jangseongbin.supplieswiki.user.repository

import io.jangseongbin.supplieswiki.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
