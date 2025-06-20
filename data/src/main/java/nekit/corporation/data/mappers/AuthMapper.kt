package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.RegisterRequest
import nekit.corporation.domain.models.auth.TokenResponse
import nekit.corporation.domain.models.auth.User

fun RegisterRequest.toRegisterRequestDto() =
    nekit.corporation.data.remote_source.dto.auth.RegisterRequestDto(
        email = email,
        password = password,
        username = username
    )

fun nekit.corporation.data.remote_source.dto.auth.TokenResponseDto.toTokenResponse() = TokenResponse(jwt = jwt, user = user.toUser())

fun nekit.corporation.data.remote_source.dto.user.UserDto.toUser() = User(
    id = id,
    documentId = documentId,
    username = username,
    email = email,
    provider = provider,
    confirmed = confirmed,
    blocked = blocked,
    createdAt = createdAt,
    updatedAt = updatedAt,
    publishedAt = publishedAt
)

fun RefreshRequest.toRefreshRequestDto() =
    RefreshRequestDto(
        identifier = identifier,
        password = password
    )

fun RefreshRequestDto.toRefreshRequest() =
   RefreshRequest(
        identifier = identifier,
        password = password
    )



