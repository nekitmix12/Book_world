package nekit.corporation.domain.models

data class User(
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
