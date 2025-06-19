package nekit.corporation.data.remote_source.dto.quote

import kotlinx.serialization.Serializable

@Serializable
data class CreateQuoteDto(
    val data: nekit.corporation.data.remote_source.dto.quote.ShortQuoteDto
)
