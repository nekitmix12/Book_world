package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetBooksByAuthorUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetBooksByAuthorUseCase.Request, GetBooksByAuthorUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getBooksByAuthor(request.authorId))
    }

    data class Request(val authorId: Long) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response

}