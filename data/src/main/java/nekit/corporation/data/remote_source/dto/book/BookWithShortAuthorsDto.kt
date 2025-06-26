package nekit.corporation.data.remote_source.dto.book

import kotlinx.serialization.Serializable

@Serializable
data class BookWithShortAuthorsDto(
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
    val authors: List<ShortAuthorDto>
)
