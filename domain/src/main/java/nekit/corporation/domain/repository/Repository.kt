package nekit.corporation.domain.repository

import nekit.corporation.domain.models.auth.RegisterRequest
import nekit.corporation.domain.models.auth.TokenResponse

interface Repository {
    suspend  fun register(registerRequest: RegisterRequest): TokenResponse
}