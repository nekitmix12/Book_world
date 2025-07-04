package nekit.corporation.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.gulya.anvil.assisted.ContributesAssistedFactory
import nekit.corporation.auth.AuthStore.Intent
import nekit.corporation.common.AppScope
import nekit.corporation.common.utils.componentCoroutineScope
@ContributesAssistedFactory(AppScope::class, SignInComponent.Factory::class)
class SignInComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted val onComplete: () -> Unit,
    @Assisted storeFactory: StoreFactory,
    private val authFactory: AuthStore.Factory,
) : ComponentContext by componentContext, SignInComponent {
    private val store =
        instanceKeeper.getStore {
            authFactory(
                storeFactory = storeFactory,
            ).create()
        }
    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = store.stateFlow

    private val scope = componentCoroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    AuthStore.Label.SubmissionFailed -> {}
                    AuthStore.Label.SubmittedSuccessfully -> onComplete()
                }
            }
        }
    }

    override fun onSignInClick() {
        store.accept(Intent.SignInClick)
    }

    override fun onEmailChange(email: String) {
        store.accept(Intent.EmailChange(email))
    }

    override fun onNameChange(name: String) {
        store.accept(Intent.NameChange(name))
    }

    override fun onPasswordChange(password: String) {
        store.accept(Intent.PasswordChange(password))
    }

    override fun onPasswordImageClick() {
        store.accept(Intent.PasswordImageClick)
    }

    override fun onEmailImageClick() {
        store.accept(Intent.EmailImageClick)
    }

    override fun onNameImageClick() {
        store.accept(Intent.NameImageClick)
    }


}