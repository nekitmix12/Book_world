package nekit.corporation.library

import kotlinx.collections.immutable.ImmutableList

data class LibraryState(
    val newBooks:ImmutableList<NewCarouselModel>,
    val popularBooks:ImmutableList<PopularBookModel>,
)
