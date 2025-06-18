package nekit.corporation.data.api

import nekit.corporation.data.dto.auth.RegisterRequestDto
import nekit.corporation.data.dto.auth.TokenResponseDto
import retrofit2.http.POST

internal interface AuthApi {
    @POST
    fun register(registerRequestDto: RegisterRequestDto): TokenResponseDto

    @POST
    fun refreshToken(registerRequestDto: RegisterRequestDto): TokenResponseDto
}