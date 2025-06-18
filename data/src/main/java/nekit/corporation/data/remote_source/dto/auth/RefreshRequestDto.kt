package nekit.corporation.data.remote_source.dto.auth

data class RefreshRequestDto(
    val identifier: String,
    val password: String
)
