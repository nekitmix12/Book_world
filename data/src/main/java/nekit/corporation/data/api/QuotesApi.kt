package nekit.corporation.data.api

import nekit.corporation.data.dto.quote.CreateQuoteDto
import nekit.corporation.data.dto.quote.QuoteDto
import nekit.corporation.data.dto.quote.QuotesDto
import retrofit2.http.GET
import retrofit2.http.POST

internal interface QuotesApi {

    @GET("quotes")
    fun getQuotes(): QuotesDto

    @POST("quotes")
    fun createQuote(createQuoteDto: CreateQuoteDto): QuoteDto
}