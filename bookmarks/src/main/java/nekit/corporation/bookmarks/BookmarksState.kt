package nekit.corporation.bookmarks

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.bookmarks.model.QuoteModel
import nekit.corporation.bookmarks.model.ReadNowBookModel
import nekit.corporation.common.models.BookSearchModel

data class BookmarksState(
    val quotes: ImmutableList<QuoteModel>,
    val books: ImmutableList<BookSearchModel>,
    val reading: ReadNowBookModel?,

    )
