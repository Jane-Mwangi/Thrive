package design.fiti.thrive.domain.model

import design.fiti.thrive.data.remote.dto.UserDto

data class User(
    val email: String,
    val password: String
) {
    fun toUserDto(): UserDto {
        return UserDto(email = email, password = password)
    }
}
