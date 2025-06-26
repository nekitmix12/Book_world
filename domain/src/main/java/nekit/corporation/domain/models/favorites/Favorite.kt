package nekit.corporation.domain.models.favorites

import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val id: Long,
    val documentId: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val bookId: Long
)
