package nekit.corporation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSignInComponent() : SignInComponent {

    override val state = MutableStateFlow(
        SignInState(
            email = "",
            password = "",
            inProgress = false,
            carouselImages = persistentListOf(),
            userName = "",
        )
    )

    override fun onSignInClick() = Unit

    override fun onEmailChange(email: String) = Unit

    override fun onPasswordChange(password: String) = Unit


}

@Preview(showSystemUi = true)
@Composable
fun SignInUiPreview() {
    SignInUi(FakeSignInComponent())
}
