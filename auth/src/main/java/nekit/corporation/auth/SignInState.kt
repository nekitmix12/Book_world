package nekit.corporation.auth

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.collections.immutable.ImmutableList

data class SignInState(
    val email: String,
    val emailError: Int? = null,
    val password: String,
    val passwordError: Int? = null,
    val userName: String,
    val userNameError: Int? = null,
    val inProgress: Boolean,
    val carouselImages: ImmutableList<ImageBitmap>
)
