package nekit.corporation.domain.usecases.books

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.data.remote_source.dto.progress.Progresses
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetReadingBookUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetReadingBookUseCase.Request, GetReadingBookUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getProgress())
    }

    data object Request : UseCase.Request
    data class Response(val books: Progresses) : UseCase.Response
}