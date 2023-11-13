package io.jangseongbin.supplieswiki.user.service

import io.jangseongbin.supplieswiki.core.exception.ConflictException
import io.jangseongbin.supplieswiki.core.exception.NotFoundException
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

    fun getUser(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { NotFoundException("유저 정보를 찾을 수 없습니다.") }
    }

    @Transactional
    fun signUp(request: SignUpRequest) {
        checkDuplicateLoginId(request.loginId)
        checkDuplicateNickname(request.nickname)

        val signUp = SignUp(
            loginId = request.loginId,
            nickname = request.nickname,
            password = request.password,
        )

        userRepository.save(User(signUp))
    }

    private fun checkDuplicateLoginId(loginId: String) {
        val user = userRepository.findByLoginId(loginId)

        if (user != null) {
            throw ConflictException("${loginId}은 중복된 아이디 입니다.")
        }
    }

    private fun checkDuplicateNickname(nickname: String) {
        val user = userRepository.findByNickname(nickname)

        if (user != null) {
            throw ConflictException("${nickname}은 중복된 닉네임 입니다.")
        }
    }
}
