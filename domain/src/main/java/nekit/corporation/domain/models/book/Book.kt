package nekit.corporation.domain.models.book

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.author.Author

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
    val illustrationURL: String?,
    val description: String,
    val author: List<Author>
)
