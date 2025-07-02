package nekit.corporation.domain.usecases.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.models.book.BooksWithAuthors
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetBookByIdUseCase.Request, GetBookByIdUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        emit(
            Response(
                repository.getBooksById(
                    request.bookId
                )
            )
        )
    }

    data class Request(val bookId: Long) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response
}