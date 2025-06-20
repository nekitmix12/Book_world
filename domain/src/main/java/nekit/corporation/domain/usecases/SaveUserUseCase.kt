package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.repository.Repository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: Repository, configuration: Configuration
) : UseCase<SaveUserUseCase.Request, SaveUserUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        repository.encryptObject(request.userData)
        Response
    }

    data class Request(val userData: RefreshRequest) : UseCase.Request
    data object Response : UseCase.Response
}