package io.jangseongbin.supplieswiki.auth.jwt

enum class Role(val role: String) {
    USER("user");

    companion object {
        @JvmStatic
        fun from(role: String) = Role.values().first { it.role == role }
    }
}
