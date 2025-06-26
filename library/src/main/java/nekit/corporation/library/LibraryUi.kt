package nekit.corporation.library

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import nekit.corporation.common_ui.theme.PurpleGrey40
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.black
import nekit.corporation.common_ui.theme.carouselDescription
import nekit.corporation.common_ui.theme.carouselName
import nekit.corporation.common_ui.theme.popularBookAuthor
import nekit.corporation.common_ui.theme.popularBookName
import nekit.corporation.common_ui.ui_kit.Grid
import nekit.corporation.common_ui.ui_kit.MiddleLabel
import nekit.corporation.common_ui.ui_kit.TopLabel
import nekit.corporation.library.models.NewCarouselModel
import nekit.corporation.library.models.PopularBookModel

@Composable
@Preview(showSystemUi = true, device = Devices.PIXEL_7)
fun LibraryUi(component: LibraryComponent = FakeLibraryComponent()) {
    val state by component.state.collectAsState()



    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(background)
            .testTag("libraryScreen")
    ) {
        item { Spacer(Modifier.height((LocalConfiguration.current.screenHeightDp * 0.085f).dp)) }
        item { TopLabel(stringResource(R.string.library)) }
        item { Spacer(Modifier.height(20.dp)) }
        item { MiddleLabel(stringResource(R.string.nova)) }
        item { Spacer(Modifier.height(16.dp)) }
        if (state.newBooks != null) item {
            ModalCarousel(
                state.newBooks!!, component::onBookClick
            )
        }
        else item { CarouselLoading() }

        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.popular_book)) }
        if (state.popularBooks != null) item {
            PopularItems(state.popularBooks!!, component::onBookClick)

        }
        else item { PopularItemsLoading() }

    }
}


@Composable
fun ImageBox(
    newCarouselModel: NewCarouselModel,
    onClick: (Long) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))

            .clickable { onClick(newCarouselModel.id) }
            .testTag("carousel model")) {
        AsyncImage(
            model = newCarouselModel.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
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
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
                    .testTag("description"),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = carouselDescription
            )
            Text(
                text = newCarouselModel.name.uppercase(),
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .testTag("name"),
                maxLines = 2,
                style = carouselName

            )
        }


    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ModalCarousel(
    items: List<NewCarouselModel>,
    onClick: (Long) -> Unit,

    ) {
    val pagerState =
        rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })

    val pageSize = LocalConfiguration.current.screenWidthDp * 0.62136f
    val pagePadding = ((LocalConfiguration.current.screenWidthDp * (1 - 0.62136f)) / 2)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .height(pageSize.dp)
            .testTag("horizontal pager"),
        pageSize = PageSize.Fixed(pageSize.dp),
        pageSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = pagePadding.dp),
    ) {
        if (items.isNotEmpty()) ImageBox(items[it % items.size], onClick)

    }

}

@Composable
fun CarouselLoading() {
    val pagerState =
        rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })

    val pageSize = LocalConfiguration.current.screenWidthDp * 0.62136f
    val pagePadding = ((LocalConfiguration.current.screenWidthDp * (1 - 0.62136f)) / 2)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .height(pageSize.dp)
            .testTag("horizontal pager"),
        pageSize = PageSize.Fixed(pageSize.dp),
        pageSpacing = (-22).dp,
        contentPadding = PaddingValues(horizontal = pagePadding.dp),
    ) {
        LoadingNew()
    }
}

@Composable
fun LoadingNew() {
    Column(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(accent_medium)
            .shimmer()
    ) {
        Spacer(Modifier.weight(1f))
        Spacer(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(6.dp))
                .background(PurpleGrey40)
                .height(16.dp)

        )
        Spacer(Modifier.height(8.dp))
        Spacer(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(6.dp))
                .background(PurpleGrey40)
                .height(16.dp)
        )

    }
}

@Composable
fun PopularItemsLoading() {
    Grid(
        3,
        2,
        persistentListOf<@Composable (Modifier) -> Unit>(
            { modifier -> PopularItemLoading(modifier) },
            { modifier -> PopularItemLoading(modifier) },
            { modifier -> PopularItemLoading(modifier) },
            { modifier -> PopularItemLoading(modifier) },
            { modifier -> PopularItemLoading(modifier) },
            { modifier -> PopularItemLoading(modifier) },
        ),
        Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 4.dp),
        Modifier.padding(horizontal = 12.dp)
    )
}

@Composable
fun PopularItemLoading(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .shimmer()
    ) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 5f)
                .clip(RoundedCornerShape(10.dp))
                .background(accent_medium)
        )
        Spacer(
            Modifier
                .height(16.dp)
                .fillMaxWidth(0.5f)
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(accent_medium)
        )
    }
}

@Composable
fun PopularItem(
    item: PopularBookModel, onClick: (Long) -> Unit, modifier: Modifier
) {
    Column(
        modifier
            .padding(top = 16.dp)
            .clickable { onClick(item.id) }
            ) {

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4.5f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
        Text(item.name, style = popularBookName)
        Text(item.author, style = popularBookAuthor)
    }
}


@Composable
fun PopularItems(
    list: ImmutableList<PopularBookModel>, onClick: (Long) -> Unit
) {
    Grid(
        column = 3,
        row = list.size / 3,
        element = list.map<PopularBookModel, @Composable (Modifier) -> Unit> { model ->
            @Composable { m: Modifier ->
                PopularItem(model, onClick, m)
            }
        }.toImmutableList(),
        modifier = Modifier.padding(horizontal = 16.dp),
        elementModifier = Modifier.padding(),
        dividerSize = 16 to 16
    )
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
