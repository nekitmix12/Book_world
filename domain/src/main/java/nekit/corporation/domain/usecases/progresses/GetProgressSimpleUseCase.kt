package nekit.corporation.domain.usecases.progresses

import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.repository.AuthorizeRepository
import nekit.corporation.domain.usecases.UseCase
import nekit.corporation.domain.usecases.auth.TokenRefreshUseCase
import javax.inject.Inject

class GetProgressSimpleUseCase @Inject constructor(
    private val repository: AuthorizeRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    configuration: Configuration
) : UseCase<GetProgressSimpleUseCase.Request, GetProgressSimpleUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        tokenRefreshUseCase.process()
        val progresses = repository.getProgress().data.map { it.chapterId }.toSet()
        var i = request.progresses.size - 1
        while (i > 0) {
            if (progresses.contains(request.progresses[i].data[0].id))
                break
            i--
        }
        emit(Response(i))
    }


    data class Request(val progresses: List<ChaptersWithBookAndAuthor>) :
        UseCase.Request

    data class Response(val progressNum: Int) :
        UseCase.Response

    companion object {
        private const val TAG = "GetProgressUseCase"
    }
}