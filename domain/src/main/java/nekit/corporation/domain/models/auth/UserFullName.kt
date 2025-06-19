package nekit.corporation.domain.models.auth

data class UserFullName(
    val name: String,
    val surname: String,
    val patronymic: String?
)
