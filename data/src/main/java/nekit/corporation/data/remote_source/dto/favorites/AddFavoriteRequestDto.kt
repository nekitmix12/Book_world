package nekit.corporation.data.remote_source.dto.favorites

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.book.BookId

@Serializable
class AddFavoriteRequestDto(
    val data: BookId
)