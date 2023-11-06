package io.jangseongbin.supplieswiki.user.domain.fixtures

import io.jangseongbin.supplieswiki.user.domain.SignUp
import io.jangseongbin.supplieswiki.user.domain.User

fun signUp(
    loginId: String,

    nickname: String,

    password: String,
): SignUp {
    return SignUp(
        loginId = loginId,
        nickname = nickname,
        password = password,
    )
}

fun user(signUp: SignUp) = User(signUp)
