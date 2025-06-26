package nekit.corporation.library

import nekit.corporation.domain.models.book.BookWithAuthor
import nekit.corporation.library.models.NewCarouselModel
import nekit.corporation.library.models.PopularBookModel

fun BookWithAuthor.toPopularBook() =
    PopularBookModel(
        id = id,
        imageUrl = coverURL,
        author = if (author.isNotEmpty()) author[0].name else "",
        name = title
    )

fun BookWithAuthor.toNewCarouselBooks() =
    NewCarouselModel(
        id = id,
        imageUrl = coverURL,
        description = description,
        name = title
    )