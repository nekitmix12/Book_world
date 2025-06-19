package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.data.remote_source.dto.quote.QuoteDto
import nekit.corporation.data.remote_source.dto.quote.QuotesDto
import nekit.corporation.domain.models.quote.Quotes

fun QuotesDto.toQuotes() = Quotes(data.map { it.toQuote() }, meta?.toMeta())

fun QuoteDto.toQuote() = Quote(id, documentId, text, createdAt, updatedAt, publishedAt, bookId)