package nekit.corporation.bookmarks

import nekit.corporation.bookmarks.model.ReadNowBookModel
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.models.progress.Progress

fun Progress.toReadNowBookModel(chapters: ChaptersWithBookAndAuthor) = ReadNowBookModel(
    id = chapters.data[0].book.id,
    name = chapters.data[0].book.title,
    partInt = value,
    part = chapters.data[0].title,
    imageUrl = chapters.data[0].book.illustrationURL.toString()
)

fun List<Books>.toSearchModel() = this.filter { it.data.isNotEmpty() }.map {
    val book = it.data[0]
    BookSearchModel(
        book.id,
        book.title,
        if (book.author.isNotEmpty()) book.author[0].name else "",
        book.illustrationURL.toString()
    )
}
