package hits.tsu.presentation.models

import android.graphics.drawable.Drawable
import hits.tsu.presentation.recycler_view.Item

data class NewCarouselModel(
    val image: Drawable,
    val description: String,
    val name: String,
) : Item