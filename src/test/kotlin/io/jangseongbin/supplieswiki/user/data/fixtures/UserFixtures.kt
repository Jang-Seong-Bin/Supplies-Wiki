package io.jangseongbin.supplieswiki.user.data.fixtures

import io.jangseongbin.supplieswiki.user.data.SignUp
import io.jangseongbin.supplieswiki.user.data.User

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
