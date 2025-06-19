package nekit.corporation.data.repository

import com.squareup.anvil.annotations.ContributesBinding
import nekit.corporation.common.AppScope
import nekit.corporation.data.mappers.toRefreshRequestDto
import nekit.corporation.data.mappers.toTokenResponse
import nekit.corporation.data.remote_source.api.AuthApi
import nekit.corporation.data.remote_source.api.BookApi
import nekit.corporation.data.remote_source.api.FavoritesApi
import nekit.corporation.data.remote_source.api.ProgressApi
import nekit.corporation.data.remote_source.api.QuotesApi
import nekit.corporation.domain.models.RefreshRequest
import nekit.corporation.domain.models.TokenResponse
import nekit.corporation.domain.repository.AuthorizeRepository
import javax.inject.Inject

@ContributesBinding(
    AppScope::class,
    boundType = AuthorizeRepository::class
)
class AuthorizeRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val favoritesApi: FavoritesApi,
    private val progressApi: ProgressApi,
    private val quotesApi: QuotesApi,
    private val authApi: AuthApi
) : AuthorizeRepository {

    override suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse =
        authApi.refreshToken(refreshRequest.toRefreshRequestDto()).toTokenResponse()
}