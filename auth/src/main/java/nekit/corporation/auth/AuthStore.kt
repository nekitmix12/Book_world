package nekit.corporation.auth

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.collections.immutable.ImmutableList
import nekit.corporation.auth.AuthStore.Intent
import nekit.corporation.auth.AuthStore.Label
import nekit.corporation.auth.AuthStore.State

interface AuthStore : Store<Intent, State, Label> {
    sealed class Intent {
        data object SignInClick : Intent()
        data class EmailChange(val email: String) : Intent()
        data class NameChange(val name: String) : Intent()
        data class PasswordChange(val password: String) : Intent()
        data object PasswordImageClick : Intent()
        data object EmailImageClick : Intent()
        data object NameImageClick : Intent()
    }

    data class State(
        val email: String = "",
        val emailError: Int? = null,
        val password: String = "",
        val passwordError: Int? = null,
        val userName: String = "",
        val userNameError: Int? = null,
        val inProgress: Boolean = false,
        val carouselImages: ImmutableList<ImageBitmap>,
        val passwordIconRes: Int? = null,
        val nameIconRes: Int? = null,
        val emailIconRes: Int? = null,
        val passwordImageTransformation: VisualTransformation = PasswordVisualTransformation(),
    ){
        val isButtonActive: Boolean
            get() =
                email.isNotBlank() && emailError == null &&
                        userName.isNotBlank()  && userNameError == null &&
                        password.isNotBlank() && passwordError == null
    }

    sealed interface Action {
        data object LoadRefreshToken : Action
    }

    sealed interface Msg {
        data object Loading : Msg
        data class EmailChanged(val email: String) : Msg
        data class UsernameChanged(val username: String) : Msg
        data class PaswordChanged(val pasword: String) : Msg
        data class EmailError(@StringRes val messageId: Int) : Msg
        data class PasswordError(@StringRes val messageId: Int) : Msg
        data class UsernameError(@StringRes val messageId: Int) : Msg
        data object PasswordTranspform : Msg
        data object Confirm : Msg

    }

    sealed interface Label {
        data object SubmittedSuccessfully : Label
        data object SubmissionFailed : Label
    }

    fun interface Factory {
        operator fun invoke(storeFactory: StoreFactory): AuthStoreFactory
    }
}