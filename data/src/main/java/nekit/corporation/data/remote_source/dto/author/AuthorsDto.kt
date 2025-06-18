package nekit.corporation.data.remote_source.dto.author

import nekit.corporation.data.remote_source.dto.common.MetaDto

data class AuthorsDto(
    val data:List<nekit.corporation.data.remote_source.dto.author.AuthorDto>,
    val meta: nekit.corporation.data.remote_source.dto.common.MetaDto
)
