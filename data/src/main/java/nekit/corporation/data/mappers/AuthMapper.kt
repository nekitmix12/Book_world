package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto
import nekit.corporation.data.remote_source.dto.auth.RegisterRequestDto
import nekit.corporation.data.remote_source.dto.auth.TokenResponseDto
import nekit.corporation.data.remote_source.dto.user.UserDto
import nekit.corporation.domain.models.RefreshRequest
import nekit.corporation.domain.models.RegisterRequest
import nekit.corporation.domain.models.TokenResponse
import nekit.corporation.domain.models.User

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
    nekit.corporation.data.remote_source.dto.auth.RefreshRequestDto(
        identifier = identifier,
        password = password
    )