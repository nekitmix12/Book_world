package nekit.corporation.domain.usecases.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.BookId
import nekit.corporation.domain.models.favorites.AddFavoriteRequest
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class AddFavoritesBooksUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<AddFavoritesBooksUseCase.Request, AddFavoritesBooksUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.addFavorites(request.bookId))
    }

    data class Request(val bookId: BookId) : UseCase.Request
    data class Response(val request: AddFavoriteRequest) : UseCase.Response
}