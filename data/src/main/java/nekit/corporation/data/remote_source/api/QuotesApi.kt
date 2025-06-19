package nekit.corporation.data.remote_source.api

import nekit.corporation.domain.models.quote.CreateQuote
import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.data.remote_source.dto.quote.QuoteDto
import nekit.corporation.data.remote_source.dto.quote.QuotesDto
import nekit.corporation.domain.models.quote.Quotes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuotesApi {

    @GET("quotes")
    suspend fun getQuotes(): QuotesDto

    @POST("quotes")
    suspend  fun createQuote(@Body createQuoteDto: CreateQuote): QuoteDto
}