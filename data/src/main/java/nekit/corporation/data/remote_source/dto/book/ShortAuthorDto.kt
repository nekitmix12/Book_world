package nekit.corporation.data.remote_source.dto.book

import kotlinx.serialization.Serializable

@Serializable
data class ShortAuthorDto(
    val id: Long,
    val documentId: String,
    val name: String,
    val avatarURL: String
)
