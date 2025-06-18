package nekit.corporation.data.remote_source.dto.book

internal data class ChapterDto(
    val id: Long,
    val documentId: String,
    val text: String,
    val title: String,
    val order: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
