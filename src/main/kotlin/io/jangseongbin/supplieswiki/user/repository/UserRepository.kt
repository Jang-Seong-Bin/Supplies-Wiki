package io.jangseongbin.supplieswiki.user.repository

import io.jangseongbin.supplieswiki.user.data.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
