package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.models.TokenResponse
import nekit.corporation.domain.repository.Repository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: Repository, configuration: Configuration
) : UseCase<RegisterUseCase.Request, RegisterUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        flow {
            Response(repository.register(request.registerRequest))
        }

    data class Request(val registerRequest: RegisterRequest) : UseCase.Request
    data class Response(val tokenResponse: TokenResponse) : UseCase.Response
}