package nekit.corporation.auth

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.common.AppScope

@ContributesAssistedFactory(AppScope::class, SignInComponent.Factory::class)
class SignInComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    val onComplete: () -> Unit
) : ComponentContext by componentContext, SignInComponent {
    override val state = MutableStateFlow(
        SignInState(
            email = "",
            password = "",
            inProgress = false,
            carouselImages = persistentListOf()
        )
    )

    override fun onSignInClick() {

    }

    override fun onEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    override fun onPasswordChange(password: String) {
        state.value = state.value.copy(password = password)
    }
}