package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.repository.AuthorizeRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: AuthorizeRepository, configuration: Configuration
) : UseCase<GetBooksUseCase.Request, GetBooksUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        flow {
            Response(repository.getBooks(request.page, request.pageSize))
        }

    data class Request(val pageSize: Int, val page: Int) : UseCase.Request
    data class Response(val books: Books) : UseCase.Response
}