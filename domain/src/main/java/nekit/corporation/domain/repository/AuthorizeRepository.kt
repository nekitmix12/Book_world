package nekit.corporation.domain.repository

import nekit.corporation.domain.models.RefreshRequest
import nekit.corporation.domain.models.TokenResponse
interface AuthorizeRepository {
    fun refreshToken(refreshRequest: RefreshRequest): TokenResponse
}