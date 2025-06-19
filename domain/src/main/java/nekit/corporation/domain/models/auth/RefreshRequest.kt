package nekit.corporation.domain.models.auth

data class RefreshRequest(    val identifier: String,
                              val password: String)
