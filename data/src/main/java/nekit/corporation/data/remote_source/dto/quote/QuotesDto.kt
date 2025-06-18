package nekit.corporation.data.remote_source.dto.quote

import nekit.corporation.data.remote_source.dto.common.MetaDto

data class QuotesDto(
    val data: List<nekit.corporation.data.remote_source.dto.quote.QuoteDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto?
)
