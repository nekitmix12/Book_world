package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hits.tsu.R
import hits.tsu.presentation.models.NewCarouselModel
import hits.tsu.presentation.models.PopularBookModel
import hits.tsu.presentation.theme.LibraryLabelText
import hits.tsu.presentation.theme.popularBookAuthor
import hits.tsu.presentation.theme.popularBookName


@Composable
fun LibraryScreen() {
    val popularBooks = listOf(
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
        PopularBookModel(
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
    )
    LazyColumn {
        item { TopLabel(stringResource(R.string.library)) }
        item { MiddleLabel(stringResource(R.string.nova)) }

        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.popular_book)) }
        items(popularBooks.size / 3) {
            ListPopularBook(popularBooks.slice(it * 3..it * 3 + 2))
        }
        if (popularBooks.size % 3 != 0) item { ListPopularBook(popularBooks.slice((popularBooks.size / 3 * 3)..<popularBooks.size)) }
    }
}

@Composable
fun CarouselNew(
    listItems: List<NewCarouselModel> = listOf(
        NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Долгожданное продолжение «Голодных игр»", "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Долгожданное продолжение «Голодных игр»", "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Долгожданное продолжение «Голодных игр»", "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Долгожданное продолжение «Голодных игр»", "рассвет жатвы"
        ), NewCarouselModel(
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Долгожданное продолжение «Голодных игр»", "рассвет жатвы"
        )
    ),
) {

    
}


@Composable
fun TopLabel(label: String ) {
    Text(text = label.uppercase(), Modifier.padding(horizontal = 16.dp), style = LibraryLabelText)
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
            image, "", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
        )
        Column {
            Spacer(Modifier.weight(1f, true))
            Text(
                text = description,
                modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                text = name, modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
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
    Column(Modifier.padding(top = 16.dp)) {
        Image(item.image, "", modifier = Modifier.clip(RoundedCornerShape(8.dp)))
        Text(item.name, style = popularBookName)
        Text(item.author, style = popularBookAuthor)
    }
}


@Composable
fun ListPopularBook(list: List<PopularBookModel>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            PopularItem(list[0])
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = if (list.size > 1) Modifier
                .weight(1f)
                .fillMaxHeight()
            else Modifier.weight(1f), contentAlignment = Alignment.Center
        ) {
            if (list.size > 1) PopularItem(list[1])
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = if (list.size > 2) Modifier
                .weight(1f)

                .fillMaxHeight()
            else Modifier.weight(1f), contentAlignment = Alignment.Center
        ) {
            if (list.size > 2) PopularItem(list[2])
        }
    }
}