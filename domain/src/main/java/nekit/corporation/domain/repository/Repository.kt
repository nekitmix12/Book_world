package nekit.corporation.domain.repository

import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.models.TokenResponse

interface Repository {
    suspend  fun register(registerRequest: RegisterRequest): TokenResponse
}