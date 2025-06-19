package nekit.corporation.data.remote_source.dto.quote

import kotlinx.serialization.Serializable

@Serializable
data class ShortQuoteDto(
    val text: String,
    val bookId: Long
)