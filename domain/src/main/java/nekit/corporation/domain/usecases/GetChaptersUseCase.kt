package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetChaptersUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetChaptersUseCase.Request, GetChaptersUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        emit(Response(repository.getChapterByBookId(request.bookId)))
    }

    data class Request(val bookId: Long) : UseCase.Request
    data class Response(val chapters: ChaptersWithBookAndAuthor) : UseCase.Response
    companion object {
        private const val TAG = "GetFavoriteUseCase"
    }
}