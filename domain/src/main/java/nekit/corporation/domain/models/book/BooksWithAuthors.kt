package nekit.corporation.domain.models.book

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class BooksWithAuthors(
    val data: List<BookWithAuthor>,
    val meta: Meta
)