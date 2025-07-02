package nekit.corporation.domain.usecases.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class ContainsFavoriteUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<ContainsFavoriteUseCase.Request, ContainsFavoriteUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        emit(Response(repository.getFavorites().data.map { it.bookId }.contains(request.bookId)))
    }

    data class Request(val bookId: Long) : UseCase.Request
    data class Response(val contains: Boolean) : UseCase.Response
    companion object {
        private const val TAG = "ContainsFavoriteUseCase"
    }
}