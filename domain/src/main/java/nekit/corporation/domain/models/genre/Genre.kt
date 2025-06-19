package nekit.corporation.data.remote_source.dto.genre

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long,
    val documentId: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
)
