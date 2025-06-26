package nekit.corporation.domain.usecases.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.favorites.Favorites
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class FavoritesBooksUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<FavoritesBooksUseCase.Request, FavoritesBooksUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getFavorites())
    }

    data object Request : UseCase.Request
    data class Response(val books: Favorites) : UseCase.Response
}