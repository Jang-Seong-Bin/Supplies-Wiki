package io.jangseongbin.supplieswiki.user.data

import io.jangseongbin.supplieswiki.user.data.fixtures.signUp
import io.jangseongbin.supplieswiki.user.data.fixtures.user
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserTest : BehaviorSpec({
    Given("유저의 정보가 주어질 때") {
        val loginId = "jangseongbin"
        val nickname = "장성빈"

        When("패스워드가 영어로만 존재한다면") {
            val signUp = signUp(
                loginId = loginId,
                nickname = nickname,
                password = "jangseongbin"
            )

            val result = shouldThrow<IllegalStateException> { user(signUp) }

            Then("IllegalStateException 에러가 발생한다.") {
                result shouldBe IllegalStateException("비밀번호 패턴이 맞지 않습니다.")
            }
        }

        When("패스워드가 숫자로만 존재한다면") {
            val signUp = signUp(
                loginId = loginId,
                nickname = nickname,
                password = "1234567890"
            )

            val result = shouldThrow<IllegalStateException> { user(signUp) }

            Then("IllegalStateException 에러가 발생한다.") {
                result shouldBe IllegalStateException("비밀번호 패턴이 맞지 않습니다.")
            }
        }

        When("패스워드가 영어, 숫자가 골고루 들어간다면") {
            val signUp = signUp(
                loginId = loginId,
                nickname = nickname,
                password = "jangseongbin123"
            )

            val result = user(signUp)

            Then("새로운 유저가 초기화 된다.") {
                result.loginId shouldBe "jangseongbin"
                result.nickname shouldBe "장성빈"
                BCryptPasswordEncoder().matches("jangseongbin123", result.password.password) shouldBe true
            }
        }
    }
})
