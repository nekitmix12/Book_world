package nekit.corporation.domain.models

data class TokenResponse(
    val jwt: String,
    val user: User,
)
