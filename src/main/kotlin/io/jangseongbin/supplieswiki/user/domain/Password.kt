package io.jangseongbin.supplieswiki.user.domain

import jakarta.persistence.Access
import jakarta.persistence.AccessType.FIELD
import jakarta.persistence.Embeddable
import java.util.regex.Pattern
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Access(FIELD)
@Embeddable
class Password(password: String) {
    val password: String

    init {
        val passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$")
        val passwordEncoder = BCryptPasswordEncoder()

        check(passwordPattern.matcher(password).matches()) { "비밀번호 패턴이 맞지 않습니다." }

        this.password = passwordEncoder.encode(password)
    }
}
