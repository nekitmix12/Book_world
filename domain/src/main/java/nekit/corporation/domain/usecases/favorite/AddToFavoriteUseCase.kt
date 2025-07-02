package nekit.corporation.domain.usecases.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.BookId
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<AddToFavoriteUseCase.Request, AddToFavoriteUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        repository.addFavorites(BookId(request.bookId))
        emit(Response)
    }

    data class Request(val bookId: Long) : UseCase.Request
    data object Response : UseCase.Response
    companion object {
        private const val TAG = "GetFavoriteUseCase"
    }
}