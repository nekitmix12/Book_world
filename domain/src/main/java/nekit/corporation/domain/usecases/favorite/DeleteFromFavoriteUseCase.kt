package nekit.corporation.domain.usecases.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class DeleteFromFavoriteUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<DeleteFromFavoriteUseCase.Request, DeleteFromFavoriteUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        repository.deleteFavorites(request.documentBookId)
        emit(Response)
    }

    data class Request(val documentBookId: String) : UseCase.Request
    data object Response : UseCase.Response
    companion object {
        private const val TAG = "GetFavoriteUseCase"
    }
}