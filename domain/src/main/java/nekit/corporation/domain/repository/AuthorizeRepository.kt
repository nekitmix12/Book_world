package nekit.corporation.domain.repository

import nekit.corporation.domain.models.RefreshRequest
import nekit.corporation.domain.models.TokenResponse
interface AuthorizeRepository {
    suspend  fun refreshToken(refreshRequest: RefreshRequest): TokenResponse
}