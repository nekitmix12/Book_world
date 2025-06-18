package nekit.corporation.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {
    val state: StateFlow<SignInState>

    fun onSignInClick()
    fun onEmailChange(email: String)
    fun onPasswordChange(password: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onComplete: () -> Unit
        ): SignInComponent
    }
}