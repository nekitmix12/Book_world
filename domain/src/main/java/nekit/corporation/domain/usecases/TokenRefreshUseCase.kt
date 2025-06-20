package nekit.corporation.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nekit.corporation.domain.repository.Repository
import nekit.corporation.domain.utils.TokenDecoder
import javax.inject.Inject

class TokenRefreshUseCase @Inject constructor(
    private val repository: Repository, configuration: Configuration,
    private val tokenDecoder: TokenDecoder,
) : UseCase<TokenRefreshUseCase.Request, TokenRefreshUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> = flow {
        val token = repository.getToken() ?: throw IllegalArgumentException("почему то нет токена")
        val decodedToken = tokenDecoder.decodeJwtPayload(token)
        if (tokenDecoder.isRotten(decodedToken)) {
            val refreshRequest = repository.decryptObject()
                ?: throw IllegalArgumentException("почему то не декрипнусь")
            repository.safeToken(repository.refreshToken(refreshRequest).jwt)

        }
        Response
    }


    data object Request : UseCase.Request
    data object Response : UseCase.Response
}