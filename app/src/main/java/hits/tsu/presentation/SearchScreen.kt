package hits.tsu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hits.tsu.R
import hits.tsu.presentation.models.AuthorModel
import hits.tsu.presentation.models.BookSearchModel
import hits.tsu.presentation.models.GenreModel
import hits.tsu.presentation.models.RequestModel
import hits.tsu.presentation.theme.accent_dark
import hits.tsu.presentation.theme.accent_light
import hits.tsu.presentation.theme.accent_medium
import hits.tsu.presentation.theme.authorText
import hits.tsu.presentation.theme.background
import hits.tsu.presentation.theme.bookAuthorSearch
import hits.tsu.presentation.theme.bookNameSearch
import hits.tsu.presentation.theme.labelText
import hits.tsu.presentation.theme.requestText
import hits.tsu.presentation.theme.textBarText
import hits.tsu.presentation.theme.white
import java.util.UUID


@Composable
fun SearchScreen() {
    val requests = remember {
        mutableStateOf(
            listOf(
                RequestModel(
                    UUID.randomUUID().toString(), "IOS"
                ), RequestModel(
                    UUID.randomUUID().toString(), "IOS"
                ), RequestModel(
                    UUID.randomUUID().toString(), "IOS"
                )
            )
        )
    }

    val genres = listOf(
        GenreModel(UUID.randomUUID().toString(), "Класика"),
        GenreModel(UUID.randomUUID().toString(), "Фэнтези"),
        GenreModel(UUID.randomUUID().toString(), "Фантастика"),
        GenreModel(UUID.randomUUID().toString(), "Класика"),
        GenreModel(UUID.randomUUID().toString(), "Фэнтези"),
        GenreModel(UUID.randomUUID().toString(), "Фантастика"),
        GenreModel(UUID.randomUUID().toString(), "Класика"),
        GenreModel(UUID.randomUUID().toString(), "Фэнтези"),
        GenreModel(UUID.randomUUID().toString(), "Фантастика"),
    )

    val authors = listOf(
        AuthorModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Братья Стругацкие"
        ),
        AuthorModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Братья Стругацкие"
        ),
        AuthorModel(
            UUID.randomUUID().toString(), ImageBitmap.imageResource(
                R.drawable.test_new_carousel
            ),
            "Братья Стругацкие"
        ),
    )

    LazyColumn {
        item {
            SearchPanel(
                listOf(
                    BookSearchModel(
                        "SWift для детей",
                        "Мэтт Маккарти, Глория Уинквист",
                        ImageBitmap.imageResource(
                            R.drawable.test_new_carousel
                        )
                    ),
                    BookSearchModel(
                        "SWift для детей",
                        "Мэтт Маккарти, Глория Уинквист",
                        ImageBitmap.imageResource(
                            R.drawable.test_new_carousel
                        )
                    ),
                    BookSearchModel(
                        "SWift для детей",
                        "Мэтт Маккарти, Глория Уинквист",
                        ImageBitmap.imageResource(
                            R.drawable.test_new_carousel
                        )
                    ),
                ),
            )
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.last_request)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(requests.value.size) { item ->
            RequestItem(requests.value[item], { request ->
                val temp = mutableListOf<RequestModel>()
                requests.value.forEach { item ->
                    if (item != request) temp.add(item)
                }
                requests.value = temp
            })
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.genres)) }
        items(genres.size / 2) { item ->
            GenreLine(genres.slice(item * 2..item * 2 + 1))
        }
        if (genres.size % 2 == 1) {
            item { GenreLine(listOf(genres[genres.size - 1])) }
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { MiddleLabel(stringResource(R.string.authors)) }
        items(authors.size) { item ->
            AuthorItem(authors[item])
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanel(
    bookList: List<BookSearchModel>,
) {
    val searchText = remember { mutableStateOf("") }


    val isActive = remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .background(background)
    )
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(query = searchText.value, onQueryChange = { text ->
                searchText.value = text
            }, onSearch = { a ->

            }, expanded = isActive.value, onExpandedChange = { active ->
                isActive.value = active
            }, enabled = true, colors = TextFieldDefaults.colors(
                focusedTextColor = accent_dark,
                unfocusedTextColor = accent_dark,
                focusedIndicatorColor = accent_dark,
                unfocusedIndicatorColor = accent_dark
            ), placeholder = {
                Text(text = stringResource(R.string.search_by_book), style = textBarText)
            }, leadingIcon = {
                if (!isActive.value) Icon(
                    painterResource(R.drawable.find), "", tint = accent_medium
                )
                else Icon(painterResource(
                    R.drawable.back
                ), "", tint = accent_dark, modifier = Modifier.clickable {
                    isActive.value = false
                })
            }, trailingIcon = {
                if (isActive.value) Icon(painterResource(R.drawable.close),
                    "",
                    tint = accent_dark,
                    modifier = Modifier.clickable {
                        searchText.value = ""
                    })
            }, interactionSource = null, modifier = if (!isActive.value) Modifier.border(
                1.dp, accent_medium, RoundedCornerShape(32.dp)
            ) else Modifier

            )
        },
        expanded = isActive.value,
        onExpandedChange = { active ->
            isActive.value = active
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = if (!isActive.value) 16.dp else 0.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(
            containerColor = if (!isActive.value) white else background,
            dividerColor = accent_medium,
            inputFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = accent_dark, unfocusedIndicatorColor = accent_dark
            )
        ),
    ) {
        LazyColumn {
            items(bookList.size) { index ->
                val item = bookList[index]
                Spacer(Modifier.height(16.dp))
                BookSearchItem(item.name, item.author, item.image)
            }
        }
    }
}


@Stable
@Composable
fun BookSearchItem(
    bookName: String, bookAuthors: String,
    bookImage: ImageBitmap,
) {

    Row(Modifier.padding(horizontal = 16.dp)) {
        Image(
            bookImage, "", modifier = Modifier
                .height(126.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier
                .height(126.dp)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.weight(1f))
            Text(text = bookName, style = bookNameSearch)
            Text(text = bookAuthors, style = bookAuthorSearch)
            Spacer(Modifier.weight(1f))
        }
    }

}


@Composable
@Stable
fun MiddleLabel(label: String) {
    Text(
        text = label.uppercase(), style = labelText, modifier = Modifier.padding(horizontal = 16.dp)
    )
}


@Composable
@Stable
fun GenreLine(list: List<GenreModel>) {
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
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(accent_light)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = list[0].genre,
                style = bookAuthorSearch,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = if (list.size > 1) Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(accent_light)
                .fillMaxHeight()
                .padding(16.dp)
            else Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (list.size > 1)
                Text(
                    text = list[1].genre,
                    style = bookAuthorSearch,

                    )
        }
    }
}

@Composable
@Stable
fun RequestItem(request: RequestModel, onDelete: (RequestModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(accent_light)
            .padding(16.dp)
    ) {
        Icon(painterResource(R.drawable.time_back), "", tint = accent_dark)
        Text(
            text = request.name,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            style = requestText
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.close),
            "",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { onDelete(request) },
            tint = accent_dark
        )
    }
}


@Composable
fun AuthorItem(
    author: AuthorModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(accent_light)
            .height(IntrinsicSize.Min)
    ) {
        Image(
            author.img, "", modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 12.dp)
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = author.name, style = authorText)
        }
    }
}
