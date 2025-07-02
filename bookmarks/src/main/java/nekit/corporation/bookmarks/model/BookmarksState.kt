package nekit.corporation.bookmarks.model

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.common.models.BookSearchModel

data class BookmarksState(
    val quotes: ImmutableList<QuoteModel>? = null,
    val books: ImmutableList<BookSearchModel>? = null,
    val reading: List<ReadNowBookModel>? = null,
    val isLoading: Boolean = true,
)