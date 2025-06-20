package nekit.corporation.domain.utils

import org.json.JSONObject
import java.time.Instant
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TokenDecoder @Inject constructor() {

    @OptIn(ExperimentalEncodingApi::class)
    fun decodeJwtPayload(token: String): String {
        val parts = token.split(".")
        require(parts.size == 3)
        val payload = parts[1].padEnd(parts[1].length + (4 - parts[1].length % 4) % 4, '=')
        return String(Base64.decode(payload), Charsets.UTF_8)
    }

    fun isRotten(json: String): Boolean {
        val exp = JSONObject(json).getLong("exp")
        return Instant.now().epochSecond >= exp
    }
}