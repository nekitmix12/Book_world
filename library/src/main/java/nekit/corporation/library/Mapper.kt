package nekit.corporation.library

import nekit.corporation.domain.models.book.Book
import nekit.corporation.library.models.NewCarouselModel
import nekit.corporation.library.models.PopularBookModel

fun Book.toPopularBook() =
    PopularBookModel(id = id, imageUrl = coverURL, author = "Какой то автор", name = title)

fun Book.toNewCarouselBooks() =
    NewCarouselModel(
        id = id,
        imageUrl = coverURL,
        description = "Какое то очееень крутое описание",
        name = title
    )