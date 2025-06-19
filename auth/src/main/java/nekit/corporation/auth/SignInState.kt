package nekit.corporation.auth

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.collections.immutable.ImmutableList

data class SignInState(
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
    val isButtonActive: Boolean = false
)
