package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable

@Serializable
data class SaveProgress(
    val data: ShortProgress,
)
