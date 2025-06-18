package nekit.corporation.data.dto.author

internal data class AuthorDto(
    val id: Long,
    val documentId: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
)
