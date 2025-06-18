package nekit.corporation.auth

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.collections.immutable.ImmutableList

data class SignInState(
    val email: String,
    val password: String,
    val inProgress: Boolean,
    val carouselImages: ImmutableList<ImageBitmap>
)
