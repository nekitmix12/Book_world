package nekit.corporation.domain.repository

import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.RegisterRequest
import nekit.corporation.domain.models.auth.TokenResponse

interface Repository {
    suspend fun register(registerRequest: RegisterRequest): TokenResponse
    suspend fun safeToken(token: String)
    suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse
    suspend fun getToken(): String?

    suspend fun decryptObject(): RefreshRequest?
    suspend fun encryptObject(refreshRequest: RefreshRequest)
}