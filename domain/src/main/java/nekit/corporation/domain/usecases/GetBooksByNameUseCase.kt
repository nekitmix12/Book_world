package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetBooksByNameUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetBooksByNameUseCase.Request, GetBooksByNameUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getBooksByName(request.name))
    }

    data class Request(val name: String) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response
}