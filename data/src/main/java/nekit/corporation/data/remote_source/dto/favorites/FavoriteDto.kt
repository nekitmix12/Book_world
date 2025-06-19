package nekit.corporation.data.remote_source.dto.favorites

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteDto(
    val id: Long,
    val documentId: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val bookId: Long
)
