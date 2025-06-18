package nekit.corporation.auth

import nekit.corporation.common.Error
import nekit.corporation.domain.EmptyEmail
import nekit.corporation.domain.EmptyPassword
import nekit.corporation.domain.InvalidEmail
import nekit.corporation.domain.RepeatNameOrEmail
import javax.inject.Inject

class AuthErrorDecoder @Inject constructor() {
    fun decode(error: Error, getString: (Int) -> String): String {
        return when (error) {
            is EmptyPassword -> getString(R.string.empty_password)
            is EmptyEmail -> getString(R.string.empty_email)
            is InvalidEmail -> getString(R.string.incorrect_email)
            is RepeatNameOrEmail -> getString(R.string.repeat_email_or_username)
            else -> getString(R.string.strangeError)
        }
    }
}