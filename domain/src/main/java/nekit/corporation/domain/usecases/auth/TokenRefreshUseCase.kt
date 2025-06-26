package nekit.corporation.domain.usecases.auth

import android.util.Log
import nekit.corporation.domain.repository.Repository
import nekit.corporation.domain.utils.TokenDecoder
import javax.inject.Inject

class TokenRefreshUseCase @Inject constructor(
    private val repository: Repository,
    private val tokenDecoder: TokenDecoder,
) {
    suspend fun process() {
        try {
            val token =
                repository.getToken() ?: throw IllegalArgumentException("почему то нет токена")
            val decodedToken = tokenDecoder.decodeJwtPayload(token)
            if (tokenDecoder.isRotten(decodedToken)) {
                val refreshRequest = repository.decryptObject()
                    ?: throw IllegalArgumentException("почему то не декрипнусь")
                repository.safeToken(repository.refreshToken(refreshRequest).jwt)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    companion object {
        private const val TAG = "TokenRefreshUseCase"
    }
}
