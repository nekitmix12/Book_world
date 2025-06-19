package nekit.corporation.domain.models.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.book.BookId

@Serializable
class AddFavoriteRequest(
    val data: BookId
)