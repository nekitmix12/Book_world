package nekit.corporation.domain.models.book

import kotlinx.serialization.Serializable

@Serializable
internal data class Chapter(
    val id: Long,
    val documentId: String,
    val text: String,
    val title: String,
    val order: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
