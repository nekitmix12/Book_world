package nekit.corporation.data.remote_source.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String,
)
