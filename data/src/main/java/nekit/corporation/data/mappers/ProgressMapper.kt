package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.progress.Progress
import nekit.corporation.data.remote_source.dto.progress.ProgressDto
import nekit.corporation.data.remote_source.dto.progress.Progresses
import nekit.corporation.data.remote_source.dto.progress.ProgressesDto
import nekit.corporation.data.remote_source.dto.progress.SaveProgress
import nekit.corporation.data.remote_source.dto.progress.SaveProgressDto
import nekit.corporation.data.remote_source.dto.progress.ShortProgress
import nekit.corporation.data.remote_source.dto.progress.ShortProgressDto

fun ProgressesDto.toProgresses() = Progresses(data.map { it.toProgress() }, meta.toMeta())

fun ProgressDto.toProgress() =
    Progress(id, documentId, value, createdAt, updatedAt, publishedAt, chapterId)

fun SaveProgress.toSaveProgressDto() = SaveProgressDto(data.toShortProgressDto())

fun ShortProgress.toShortProgressDto() = ShortProgressDto(value, chapterId)