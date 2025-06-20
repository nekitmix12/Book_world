package nekit.corporation.data.repository

import com.squareup.anvil.annotations.ContributesBinding
import nekit.corporation.common.AppScope
import nekit.corporation.data.local_source.CryptoManager
import nekit.corporation.data.local_source.SharedPreferenceManager
import nekit.corporation.data.mappers.toRefreshRequest
import nekit.corporation.data.mappers.toRefreshRequestDto
import nekit.corporation.data.mappers.toRegisterRequestDto
import nekit.corporation.data.mappers.toTokenResponse
import nekit.corporation.data.remote_source.api.AuthApi
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.RegisterRequest
import nekit.corporation.domain.models.auth.TokenResponse
import nekit.corporation.domain.repository.Repository
import javax.inject.Inject

@ContributesBinding(
    scope = AppScope::class, boundType = Repository::class
)
class RepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val cryptoManager: CryptoManager
) : Repository {
    override suspend fun register(registerRequest: RegisterRequest): TokenResponse =
        authApi.register(registerRequest.toRegisterRequestDto()).toTokenResponse()

    override suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse =
        authApi.refreshToken(refreshRequest.toRefreshRequestDto()).toTokenResponse()

    override suspend fun safeToken(token: String) = sharedPreferenceManager.save(token)

    override suspend fun getToken(): String? = sharedPreferenceManager.getToken()


    override suspend fun decryptObject(): RefreshRequest? =
        cryptoManager.decryptObject()?.toRefreshRequest()

    override suspend fun encryptObject(refreshRequest: RefreshRequest) =
        cryptoManager.encryptObject(refreshRequest.toRefreshRequestDto())
}