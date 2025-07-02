package nekit.corporation.domain.usecases.favorite

import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetFavoriteUseCase.Request, GetFavoriteUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        val books = repository.getFavorites()
        val result = mutableListOf<Books>()
        val jobs = mutableListOf<Job>()
        coroutineScope {
            books.data.forEach {
                jobs.add(
                    launch {
                        result.add(
                            repository.getBooksById(it.bookId)
                        )
                    })
            }

        }
        jobs.forEach { it.join(); Log.d(TAG, it.key.toString()) }
        Log.i(TAG, "finish $result")
        emit(Response(result))
    }

    data object Request : UseCase.Request
    data class Response(val books: List<Books>) : UseCase.Response
    companion object {
        private const val TAG = "GetFavoriteUseCase"
    }
}