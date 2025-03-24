package hits.tsu.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hits.tsu.R
import hits.tsu.presentation.models.NewCarouselModel
import hits.tsu.presentation.models.PopularBookModel
import hits.tsu.presentation.theme.LibraryLabelText
import hits.tsu.presentation.theme.background
import hits.tsu.presentation.theme.black
import hits.tsu.presentation.theme.carouselDescription
import hits.tsu.presentation.theme.carouselName
import hits.tsu.presentation.theme.popularBookAuthor
import hits.tsu.presentation.theme.popularBookName
import java.util.UUID

@Preview(showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun LibraryScreen(navController: NavController = rememberNavController()) {
    val popularBooks = listOf(
        PopularBookModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
        PopularBookModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
        PopularBookModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
        PopularBookModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винчи"
        ),
    )

    val carouselList = listOf(
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Дэн Браун", "Ко"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Дэн Браун", "Код д"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Дэн Браун", "Код да в"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да ви"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ), "Дэн Браун", "Код да вин"
        ),
        NewCarouselModel(
            UUID.randomUUID().toString(),
            ImageBitmap.imageResource(
                R.drawable.test_book
            ), "Дэн Браун", "Код да винч"
        ),
    )

    val onPopularBookClick: (PopularBookModel) -> Unit = {
        navController.navigate(Screens.BookDetails(it.id))
    }
    val onCardClick: (NewCarouselModel) -> Unit = {
        navController.navigate(Screens.BookDetails(it.id))
    }


    LazyColumn(Modifier.background(background)) {
        item { Spacer(Modifier.height((LocalConfiguration.current.screenHeightDp * 0.085f).dp)) }
        item { TopLabel(stringResource(R.string.library)) }
        item { Spacer(Modifier.height(20.dp)) }
        item { MiddleLabel(stringResource(R.string.nova)) }
        item { Spacer(Modifier.height(16.dp)) }
        item { ModalCarousel(carouselList, onCardClick) }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.popular_book)) }
        items(popularBooks.size / 3) {
            ListPopularBook(popularBooks.slice(it * 3..it * 3 + 2), onPopularBookClick)
        }
        if (popularBooks.size % 3 != 0) item {
            ListPopularBook(
                popularBooks.slice((popularBooks.size / 3 * 3)..<popularBooks.size),
                onPopularBookClick
            )
        }
    }
}

@Composable
fun TopLabel(label: String) {
    Text(text = label.uppercase(), Modifier.padding(horizontal = 16.dp), style = LibraryLabelText)
}

@Composable
@Stable
fun ImageBox(
    newCarouselModel: NewCarouselModel,
    onClick: (NewCarouselModel) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))

            .clickable { onClick(newCarouselModel) }
    ) {
        Image(
            newCarouselModel.image,
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Spacer(
            Modifier
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color.Transparent, 1f to black.copy(alpha = 0.5f)
                    )
                )
                .fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(1f))
            Text(
                text = newCarouselModel.description,
                modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp),
                style = carouselDescription
            )
            Text(
                text = newCarouselModel.name.uppercase(),
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                style = carouselName

            )
        }


    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ModalCarousel(
    items: List<NewCarouselModel>,
    onClick: (NewCarouselModel) -> Unit,

    ) {
    val pagerState =
        rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })

    val pageSize = LocalConfiguration.current.screenWidthDp * 0.62136f
    val pagePadding = ((LocalConfiguration.current.screenWidthDp * (1 - 0.62136f)) / 2)

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(pageSize.dp),
            pageSize = PageSize.Fixed(pageSize.dp),
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = pagePadding.dp),
        ) {
            ImageBox(items[it % items.size], onClick)

        }
    }

}


@Composable
fun PopularItem(
    item: PopularBookModel,
    onClick: (PopularBookModel) -> Unit,
) {
    Column(
        Modifier
            .padding(top = 16.dp)
            .clickable { onClick(item) }) {
        Image(item.image, "", modifier = Modifier.clip(RoundedCornerShape(8.dp)))
        Text(item.name, style = popularBookName)
        Text(item.author, style = popularBookAuthor)
    }
}


@Composable
fun ListPopularBook(list: List<PopularBookModel>, onClick: (PopularBookModel) -> Unit) {
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
            PopularItem(list[0], onClick)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = if (list.size > 1) Modifier
                .weight(1f)
                .fillMaxHeight()
            else Modifier.weight(1f), contentAlignment = Alignment.Center
        ) {
            if (list.size > 1) PopularItem(list[1], onClick)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = if (list.size > 2) Modifier
                .weight(1f)

                .fillMaxHeight()
            else Modifier.weight(1f), contentAlignment = Alignment.Center
        ) {
            if (list.size > 2) PopularItem(list[2], onClick)
        }
    }
}

/*

@Composable
fun CarouselView(
    listItems: List<NewCarouselModel> = listOf(
        NewCarouselModel(
            ContextCompat.getDrawable(
                LocalContext.current, R.drawable.test_new_carousel
            ) ?: throw IllegalArgumentException("Not found Drawable"),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ),
        NewCarouselModel(
            ContextCompat.getDrawable(
                LocalContext.current, R.drawable.test_new_carousel
            ) ?: throw IllegalArgumentException("Not found Drawable"),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ),
        NewCarouselModel(
            ContextCompat.getDrawable(
                LocalContext.current, R.drawable.test_new_carousel
            ) ?: throw IllegalArgumentException("Not found Drawable"),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ),
        NewCarouselModel(
            ContextCompat.getDrawable(
                LocalContext.current, R.drawable.test_new_carousel
            ) ?: throw IllegalArgumentException("Not found Drawable"),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ),
        NewCarouselModel(
            ContextCompat.getDrawable(
                LocalContext.current, R.drawable.test_new_carousel
            ) ?: throw IllegalArgumentException("Not found Drawable"),
            "Долгожданное продолжение «Голодных игр»",
            "рассвет жатвы"
        ),
    ),
) {
    val context = LocalContext.current
    val recyclerView = remember { RecyclerView(context) }
    val adapters = AdapterWithDelegates(
        listOf(
            CarouselDelegate(),
        )
    )
    adapters.submitList(listItems)
    AndroidView(modifier = Modifier, factory = {
        recyclerView
    }, update = { view ->
        view.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
        view.adapter = adapters
        CarouselSnapHelper().attachToRecyclerView(view)

    })
}*/
