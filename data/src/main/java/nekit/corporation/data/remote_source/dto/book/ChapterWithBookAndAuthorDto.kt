package nekit.corporation.data.remote_source.dto.book

import kotlinx.serialization.Serializable

@Serializable
data class ChapterWithBookAndAuthorDto(
    val id: Long,
    val documentId: String,
    val text: String,
    val title: String,
    val order: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val book: BookWithShortAuthorsDto,

    )
