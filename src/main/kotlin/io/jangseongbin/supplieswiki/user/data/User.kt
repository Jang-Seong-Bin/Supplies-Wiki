package io.jangseongbin.supplieswiki.user.data

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Entity
@Table(name = "users")
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
