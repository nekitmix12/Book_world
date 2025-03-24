package hits.tsu.presentation.models

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import hits.tsu.presentation.recycler_view.Item

data class NewCarouselModel(
    val id:String,
    val image: ImageBitmap,
    val description: String,
    val name: String,
) : Item