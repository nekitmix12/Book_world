package nekit.corporation.data.remote_source.dto.auth

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String,
)
