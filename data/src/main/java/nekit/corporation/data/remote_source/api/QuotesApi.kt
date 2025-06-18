package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.quote.CreateQuoteDto
import nekit.corporation.data.remote_source.dto.quote.QuoteDto
import nekit.corporation.data.remote_source.dto.quote.QuotesDto
import retrofit2.http.GET
import retrofit2.http.POST

interface QuotesApi {

    @GET("quotes")
    fun getQuotes(): nekit.corporation.data.remote_source.dto.quote.QuotesDto

    @POST("quotes")
    fun createQuote(createQuoteDto: nekit.corporation.data.remote_source.dto.quote.CreateQuoteDto): nekit.corporation.data.remote_source.dto.quote.QuoteDto
}