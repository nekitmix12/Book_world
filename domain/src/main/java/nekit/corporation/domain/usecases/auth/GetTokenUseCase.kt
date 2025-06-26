package nekit.corporation.domain.usecases.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.repository.Repository
import nekit.corporation.domain.usecases.UseCase
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: Repository, configuration: Configuration
) : UseCase<GetTokenUseCase.Request, GetTokenUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = flow {
        Log.d(TAG,"start request")
        emit(Response(repository.getToken()))
        Log.d(TAG,"finish request")

    }

    data object Request : UseCase.Request
    data class Response(val token: String?) : UseCase.Response
    companion object{
        private const val TAG = "GetTokenUseCase"
    }
}