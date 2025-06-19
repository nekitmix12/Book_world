package nekit.corporation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSignInComponent : SignInComponent {

    override val state = MutableStateFlow(
        SignInState(
            email = "",
            password = "",
            inProgress = true,
            carouselImages = persistentListOf(),
            userName = "",
        )
    )

    override fun onSignInClick() = Unit

    override fun onEmailChange(email: String) = Unit
    override fun onNameChange(name: String) = Unit

    override fun onPasswordChange(password: String) = Unit
    override fun onPasswordImageClick() = Unit

    override fun onEmailImageClick() = Unit

    override fun onNameImageClick() = Unit


}

@Preview(showSystemUi = true, device = Devices.PIXEL_6)
@Composable
fun SignInUiPreview() {

    SignInUi(FakeSignInComponent())
}
