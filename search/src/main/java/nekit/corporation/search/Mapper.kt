package nekit.corporation.search

import kotlinx.collections.immutable.toImmutableList
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.domain.models.book.Book
import nekit.corporation.domain.models.book.Books

fun Book.toSearchModel() = BookSearchModel(
    id = id,
    name = title,
    author = "Какой то автор",
    imageUrl = coverURL,
)

fun Books.toSearchModels() =
    this.data.map { it.toSearchModel() }.toImmutableList()