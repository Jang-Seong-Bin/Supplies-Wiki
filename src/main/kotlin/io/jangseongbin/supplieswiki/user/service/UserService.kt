package io.jangseongbin.supplieswiki.user.service

import io.jangseongbin.supplieswiki.user.infrastructure.SignUp
import io.jangseongbin.supplieswiki.user.infrastructure.User
import io.jangseongbin.supplieswiki.user.infrastructure.UserRepository
import io.jangseongbin.supplieswiki.user.request.SignUpRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUsers(): List<User> = userRepository.findAll()

    fun getUser(userId: Long) = userRepository.findById(userId)

    @Transactional
    fun signUp(request: SignUpRequest) {
        val signUp = SignUp(
            loginId = request.loginId,
            nickname = request.nickname,
            password = request.password,
        )

        userRepository.save(User(signUp))
    }
}
