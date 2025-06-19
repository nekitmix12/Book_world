package nekit.corporation.data.remote_source.dto.quote

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.common.MetaDto

@Serializable
data class QuotesDto(
    val data: List<nekit.corporation.data.remote_source.dto.quote.QuoteDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto?
)
