package nekit.corporation.domain.usecases.books

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.BooksWithAuthors
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetBooksUseCase.Request, GetBooksUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        emit(Response(
            repository.getBooksWithAuthors(
                request.page, request.pageSize
            )
        ))
    }

    data class Request(val pageSize: Int, val page: Int) : UseCase.Request
    data class Response(val books: BooksWithAuthors) : UseCase.Response
}