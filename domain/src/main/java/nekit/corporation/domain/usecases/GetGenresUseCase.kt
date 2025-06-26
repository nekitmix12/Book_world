package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.data.remote_source.dto.genre.Genres
import nekit.corporation.domain.models.author.Authors
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetGenresUseCase.Request, GetGenresUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        tokenRefreshUseCase.process()
        Response(repository.getGenres())
    }

    data object Request : UseCase.Request
    data class Response(val books: Genres) : UseCase.Response

}