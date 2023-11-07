package io.jangseongbin.supplieswiki.user.data

import io.jangseongbin.supplieswiki.user.data.fixtures.signUp
import io.jangseongbin.supplieswiki.user.data.fixtures.user
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserTest : ExpectSpec({
    context("유저 생성에 있어") {
        context("유저의 로그인 아이디, 닉네임 정보가 존재할 때") {
            val loginId = "jangseongbin"
            val nickname = "장성빈"

            expect("패스워드가 영어로만 존재하면 IllegalStateException 을 리턴한다.") {
                val password = "jangseongbin"
                val signUp = signUp(
                    loginId = loginId,
                    nickname = nickname,
                    password = password,
                )

                shouldThrow<IllegalStateException> {
                    user(signUp)
                }
            }

            expect("패스워드가 숫자로만 존재하면 IllegalStateException 을 리턴한다.") {
                val password = "123456789"
                val signUp = signUp(
                    loginId = loginId,
                    nickname = nickname,
                    password = password,
                )

                shouldThrow<IllegalStateException> {
                    user(signUp)
                }
            }

            expect("패스워드가 영어, 숫자가 골고루 들어가면 정상적으로 생성된다.") {
                val password = "jangseongbin123"
                val signUp = signUp(
                    loginId = loginId,
                    nickname = nickname,
                    password = password,
                )
                val user = user(signUp)

                user.loginId shouldBe "jangseongbin"
                user.nickname shouldBe "장성빈"
                BCryptPasswordEncoder().matches("jangseongbin123", user.password.password) shouldBe true
            }
        }
    }
})
