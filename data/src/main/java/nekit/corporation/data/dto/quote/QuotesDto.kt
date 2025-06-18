package nekit.corporation.data.dto.quote

import nekit.corporation.data.dto.common.MetaDto

internal data class QuotesDto(
    val data: List<QuoteDto>,
    val meta: MetaDto?
)
