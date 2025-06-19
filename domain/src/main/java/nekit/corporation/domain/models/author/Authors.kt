package nekit.corporation.domain.models.author

import kotlinx.serialization.Serializable
import nekit.corporation.data.remote_source.dto.author.Author
import nekit.corporation.domain.models.common.Meta

@Serializable
data class Authors(
    val data:List<Author>,
    val meta: Meta
)
