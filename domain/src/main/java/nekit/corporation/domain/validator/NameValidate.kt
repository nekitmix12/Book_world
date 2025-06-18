package nekit.corporation.domain.validator

import nekit.corporation.common.Error
import nekit.corporation.domain.ShortName
import javax.inject.Inject

class NameValidate@Inject constructor() {
     fun validate(name: String): Error? {
        return if (name.isBlank()) ShortName else null
    }
}