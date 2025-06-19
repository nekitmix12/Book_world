package nekit.corporation.domain.models.auth
data class LoginRequest(
    val email: String,
    val password: String,
)
