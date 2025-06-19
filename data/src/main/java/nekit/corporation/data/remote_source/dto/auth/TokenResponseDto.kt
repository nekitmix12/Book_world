package nekit.corporation.data.remote_source.dto.auth

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.user.UserDto

@Serializable
data class TokenResponseDto(
    val jwt: String,
    val user: UserDto,
)
