package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hits.tsu.R
import hits.tsu.presentation.models.NewCarouselModel
import hits.tsu.presentation.models.PopularBookModel

@Composable
fun LibraryScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselNew(
    listItems: List<NewCarouselModel> = listOf(
        NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        )
    ),
) {

    val state = rememberCarouselState { listItems.count() }

    HorizontalMultiBrowseCarousel(
        state = state,
        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
    ) { i ->
        val item = listItems[i]
        ImageBox(item.image, item.description, item.name)
    }
}

@Composable
@Stable
fun ImageBox(
    image: ImageBitmap,
    description: String,
    name: String,
) {
    Box(
        Modifier
            .size(252.dp, 256.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            image,
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            Spacer(Modifier.weight(1f, true))
            Text(
                text = description,
                modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                text = name,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

    }
}

@Composable
fun PopularBooksList(
    books: List<PopularBookModel> = listOf(
        PopularBookModel(
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
        PopularBookModel(
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
        PopularBookModel(
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
    ),
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true,
        
    ) {
        items(books) { item ->

            PopularItem(item)
        }

    }
}

@Composable
fun PopularItem(
    item: PopularBookModel = PopularBookModel(
        ImageBitmap.imageResource(
            R.drawable.test_book
        ), "Дэн Браун", "Код да винчи"
    ),
) {
    Column {
        Image(item.image, "")
        Text(item.name)
        Text(item.author)
    }
}