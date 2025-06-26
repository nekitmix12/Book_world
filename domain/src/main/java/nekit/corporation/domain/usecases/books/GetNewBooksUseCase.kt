package nekit.corporation.domain.usecases.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetNewBooksUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    configuration: Configuration,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) : UseCase<GetNewBooksUseCase.Request, GetNewBooksUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getNewBooks(isNew = true))
    }

    data class Request(val isNew: Boolean) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response
}