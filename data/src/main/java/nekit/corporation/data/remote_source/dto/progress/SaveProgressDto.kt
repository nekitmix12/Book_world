package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable

@Serializable
data class SaveProgressDto(
    val data: nekit.corporation.data.remote_source.dto.progress.ShortProgressDto,
)
