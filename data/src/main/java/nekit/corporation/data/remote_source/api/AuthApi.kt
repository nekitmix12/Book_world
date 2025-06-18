package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto
import nekit.corporation.data.remote_source.dto.auth.RegisterRequestDto
import nekit.corporation.data.remote_source.dto.auth.TokenResponseDto
import retrofit2.http.POST

interface AuthApi {
    @POST
    fun register(registerRequestDto: RegisterRequestDto): TokenResponseDto

    @POST
    fun refreshToken(refreshRequestDto: RefreshRequestDto): TokenResponseDto
}