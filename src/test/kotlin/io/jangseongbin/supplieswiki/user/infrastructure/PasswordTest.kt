package io.jangseongbin.supplieswiki.user.infrastructure

import io.jangseongbin.supplieswiki.user.infrastructure.fixtures.password
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordTest : ExpectSpec({
    context("패스워드가 입력되었을 때") {
        expect("규칙에 맞지 않는다면 IllegalStateException 을 리턴한다.") {
            val word = "jangseongbin"

            shouldThrow<IllegalStateException> {
                password(word)
            }
        }

        expect("규칙에 맞다면 정상적으로 생성된다.") {
            val word = "jangseongbin123"
            val password = password(word)

            BCryptPasswordEncoder().matches("jangseongbin123", password.password) shouldBe true
        }
    }
})
