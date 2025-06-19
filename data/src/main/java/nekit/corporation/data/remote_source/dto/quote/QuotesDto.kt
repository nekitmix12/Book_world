package nekit.corporation.data.remote_source.dto.quote

import kotlinx.serialization.Serializable
import nekit.corporation.domain.models.common.Meta

@Serializable
data class QuotesDto(
    val data: List<QuoteDto>,
    val meta: Meta?
)
