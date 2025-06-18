package nekit.corporation.data.remote_source.dto.user

data class UserDto(
    val id: Long,
    val documentId: String,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
