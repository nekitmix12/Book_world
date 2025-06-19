package nekit.corporation.domain.models.book

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Long,
    val documentId: String,
    val title: String,
    val coverURL: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val isNew: Boolean,
    val illustrationURL: String?
)
