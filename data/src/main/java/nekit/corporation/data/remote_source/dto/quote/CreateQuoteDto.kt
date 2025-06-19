package nekit.corporation.data.remote_source.dto.quote

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.quote.ShortQuote

@Serializable
data class CreateQuoteDto(
    val data: ShortQuoteDto
)
