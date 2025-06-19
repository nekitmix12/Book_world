package nekit.corporation.domain.models.auth

data class TokenResponse(
    val jwt: String,
    val user: User,
)
