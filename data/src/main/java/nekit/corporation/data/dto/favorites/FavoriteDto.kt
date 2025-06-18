package nekit.corporation.data.dto.favorites

data class FavoriteDto(
    val id: Long,
    val documentId: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val bookId: Long
)
