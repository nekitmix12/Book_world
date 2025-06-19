package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto
import nekit.corporation.data.remote_source.dto.auth.RegisterRequestDto
import nekit.corporation.data.remote_source.dto.auth.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/local/register")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): TokenResponseDto

    @POST("api/auth/local/")
    suspend fun refreshToken(@Body refreshRequestDto: RefreshRequestDto): TokenResponseDto
}