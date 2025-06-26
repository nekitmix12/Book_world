package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.BookId
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetBooksByGenreUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetBooksByGenreUseCase.Request, GetBooksByGenreUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getBooksByGenre(request.genreId))
    }

    data class Request(val genreId: Long) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response

}