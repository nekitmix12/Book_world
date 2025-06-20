package nekit.corporation.data.remote_source.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    val identifier: String,
    val password: String
)
