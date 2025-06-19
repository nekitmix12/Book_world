package nekit.corporation.domain.models.quote

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Quotes(
    val data: List<Quote>,
    val meta: Meta?
)
