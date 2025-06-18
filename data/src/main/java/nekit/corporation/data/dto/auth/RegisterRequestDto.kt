package nekit.corporation.data.dto.auth

internal data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String,
)
