package nekit.corporation.bookmarks.model

data class QuoteModel(
    val id: Long,
    val content: String,
    val author: String,
    val book: String,
)