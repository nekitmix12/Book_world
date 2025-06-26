package nekit.corporation.domain.usecases.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.repository.Repository
import nekit.corporation.domain.usecases.UseCase
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: Repository, configuration: Configuration
) : UseCase<SaveUserUseCase.Request, SaveUserUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        repository.encryptObject(request.userData)
        repository.safeToken(request.token)
        Log.d(TAG, "saveToken")
        emit(Response)
    }

    data class Request(val userData: RefreshRequest, val token: String) : UseCase.Request
    data object Response : UseCase.Response
    companion object {
        private const val TAG = "SaveUserUseCase"
    }
}