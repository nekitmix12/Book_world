package nekit.corporation.data.dto.book

internal data class BookDto(
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
