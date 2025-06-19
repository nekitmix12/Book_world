package nekit.corporation.data.remote_source.dto.progress

import kotlinx.serialization.Serializable

@Serializable
data class Progress(
    val id: Long,
    val documentId: String,
    val value: Long,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val chapterId: Long
)
