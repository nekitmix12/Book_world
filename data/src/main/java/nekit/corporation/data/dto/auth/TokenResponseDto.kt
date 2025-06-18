package nekit.corporation.data.dto.auth

import nekit.corporation.data.dto.user.UserDto

internal data class TokenResponseDto(
    val jwt: String,
    val user: UserDto,
)
