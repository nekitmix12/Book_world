package nekit.corporation.data.dto.quote

data class QuoteDto(
    val id: Long,
    val documentId: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val bookId: Long
)
