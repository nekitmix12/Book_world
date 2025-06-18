package nekit.corporation.search

import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.search.models.AuthorModel
import nekit.corporation.search.models.GenreModel
import nekit.corporation.search.models.RequestModel

data class SearchState(
    val requests: ImmutableList<RequestModel>,
    val genres: ImmutableList<GenreModel>,
    val authors: ImmutableList<AuthorModel>,
    val searchIsOpen: Boolean,
    val searchText: String,
    val isLoading: Boolean
)
