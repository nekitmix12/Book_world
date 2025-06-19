package nekit.corporation.data.repository

import com.squareup.anvil.annotations.ContributesBinding
import nekit.corporation.common.AppScope
import nekit.corporation.data.remote_source.api.AuthApi
import nekit.corporation.data.mappers.toRegisterRequestDto
import nekit.corporation.data.mappers.toTokenResponse
import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.models.TokenResponse
import nekit.corporation.domain.repository.Repository
import javax.inject.Inject

@ContributesBinding(
    scope = AppScope::class,
    boundType = Repository::class
)
class RepositoryImpl @Inject constructor(private val authApi: AuthApi) : Repository {
    suspend  override fun register(registerRequest: RegisterRequest): TokenResponse =
        authApi.register(registerRequest.toRegisterRequestDto()).toTokenResponse()
}