package nekit.corporation.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {
    val state: StateFlow<SignInState>

    fun onSignInClick()
    fun onEmailChange(email: String)
    fun onNameChange(name: String)
    fun onPasswordChange(password: String)
    fun onPasswordImageClick()
    fun onEmailImageClick()
    fun onNameImageClick()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onComplete: () -> Unit
        ): SignInComponent
    }
}