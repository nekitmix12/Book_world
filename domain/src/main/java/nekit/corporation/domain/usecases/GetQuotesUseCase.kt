package nekit.corporation.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.domain.models.book.Book
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetQuotesUseCase.Request, GetQuotesUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        val quotes = repository.getQuotes()
        val result = mutableListOf<Pair<Quote, Book>>()
        val jobs = mutableListOf<Job>()
        quotes.data.forEach {
            coroutineScope {
                jobs.add(launch(Dispatchers.IO) {
                    result.add(it to repository.getBooksById(it.bookId).data[0])
                })
            }
        }
        jobs.forEach { it.join() }
        emit(Response(result))
    }

    data object Request : UseCase.Request
    data class Response(val books: List<Pair<Quote, Book>>) : UseCase.Response
}