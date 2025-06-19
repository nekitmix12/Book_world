package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable

@Serializable
data class ShortProgressDto(
    val value: Long,
    val chapterId: Long
)
