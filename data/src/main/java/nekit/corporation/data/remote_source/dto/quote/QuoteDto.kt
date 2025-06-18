package nekit.corporation.data.remote_source.dto.quote

data class QuoteDto(
    val id: Long,
    val documentId: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val bookId: Long
)
