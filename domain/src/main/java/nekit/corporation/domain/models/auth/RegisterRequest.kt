package nekit.corporation.domain.models.auth

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
)
