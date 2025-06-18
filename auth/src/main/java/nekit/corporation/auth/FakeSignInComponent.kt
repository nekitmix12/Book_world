package nekit.corporation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeSignInComponent() : SignInComponent {

    override val state = MutableStateFlow(
        SignInState(
            email = "",
            password = "",
            inProgress = false,
            carouselImages = persistentListOf()
        )
    )

    override fun onSignInClick()  = Unit

    override fun onEmailChange(email: String)  = Unit

    override fun onPasswordChange(password: String) = Unit


}

@Preview(showSystemUi = true)
@Composable
fun SignInUiPreview() {
    SignInUi(FakeSignInComponent())
}
