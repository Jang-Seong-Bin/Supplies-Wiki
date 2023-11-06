package io.jangseongbin.supplieswiki.user.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Entity
class User(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long = 0,

    val loginId: String,

    val nickname: String,

    @Embedded
    val password: Password,

    @CreatedDate
    val createdAt: LocalDateTime = now(),

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null,
) {
    constructor(signUp: SignUp) : this(
        loginId = signUp.loginId,
        nickname = signUp.nickname,
        password = Password(signUp.password),
    )
}
