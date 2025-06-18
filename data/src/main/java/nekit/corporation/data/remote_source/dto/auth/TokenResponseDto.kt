package nekit.corporation.data.remote_source.dto.auth

import nekit.corporation.data.remote_source.dto.user.UserDto

data class TokenResponseDto(
    val jwt: String,
    val user: nekit.corporation.data.remote_source.dto.user.UserDto,
)
