package nekit.corporation.library

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.library.models.NewCarouselModel
import nekit.corporation.library.models.PopularBookModel

data class LibraryState(
    val newBooks: ImmutableList<NewCarouselModel>,
    val popularBooks: ImmutableList<PopularBookModel>,
    val isLoading: Boolean,
    val booksPage: Int,
)
