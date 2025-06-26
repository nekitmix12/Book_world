package nekit.corporation.domain.models.book


data class ChapterWithBookAndAuthor(
    val id: Long,
    val documentId: String,
    val text: String,
    val title: String,
    val order: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val book: BookWithShortAuthors,

    )
