package nekit.corporation.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nekit.corporation.common_ui.R.drawable
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_light
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.authorText
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.bookAuthorSearch
import nekit.corporation.common_ui.theme.requestText
import nekit.corporation.common_ui.theme.textBarText
import nekit.corporation.common_ui.theme.white
import nekit.corporation.common_ui.ui_kit.BookItem
import nekit.corporation.common_ui.ui_kit.MiddleLabel
import nekit.corporation.search.models.AuthorModel
import nekit.corporation.search.models.GenreModel
import nekit.corporation.search.models.RequestModel
import java.lang.Integer.min
import nekit.corporation.common_ui.R.drawable.close

@Composable
fun SearchUi(component: SearchComponent) {

    val state by component.state.collectAsState()

    LazyColumn(Modifier.background(nekit.corporation.common_ui.theme.background).testTag("search screen")) {
        item { Spacer(Modifier.height(24.dp)) }
        item { nekit.corporation.common_ui.ui_kit.MiddleLabel(stringResource(R.string.last_request)) }
        item { Spacer(Modifier.height(8.dp)) }
        items(state.requests) {
            RequestItem(it, component::onRequestClose)
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { nekit.corporation.common_ui.ui_kit.MiddleLabel(stringResource(R.string.genres)) }
        items(state.genres.size / 2) { item ->
            GenreLine(state.genres.slice(item * 2..item * 2 + 1), component::setSearchText)
        }
        if (state.genres.size % 2 == 1) {
            item {
                GenreLine(listOf(state.genres[state.genres.size - 1]), component::setSearchText)
            }
        }
        item { Spacer(Modifier.height(24.dp)) }
        item { nekit.corporation.common_ui.ui_kit.MiddleLabel(stringResource(R.string.authors)) }
        items(state.authors.size) { item ->
            AuthorItem(state.authors[item], component::setSearchText)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanel(
    bookList: List<BookSearchModel>,
    isActive: Boolean,
    searchText: String,
    onClick: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onSearchItemClick: (String) -> Unit,

    ) {
    val searchTextLocal = remember { mutableStateOf(searchText) }
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(query = searchTextLocal.value, onQueryChange = { text ->
                searchTextLocal.value = text
            }, onSearch = onSearch, expanded = true, onExpandedChange = { active ->
                onClick(active)
                Log.d("Searchbar", "onExpandedChange: $active")

            }, enabled = true, colors = TextFieldDefaults.colors(
                focusedTextColor = nekit.corporation.common_ui.theme.accent_dark,
                unfocusedTextColor = nekit.corporation.common_ui.theme.accent_dark,
                focusedIndicatorColor = nekit.corporation.common_ui.theme.accent_dark,
                unfocusedIndicatorColor = nekit.corporation.common_ui.theme.accent_dark
            ), placeholder = {
                Text(text = stringResource(R.string.search_by_book), style = nekit.corporation.common_ui.theme.textBarText)
            }, leadingIcon = {
                if (!isActive) Icon(
                    painterResource(R.drawable.find), "", tint = nekit.corporation.common_ui.theme.accent_medium
                )
                else Icon(painterResource(
                    drawable.back
                ), "", tint = nekit.corporation.common_ui.theme.accent_dark, modifier = Modifier.clickable {
                    onClick(false)
                })
            }, trailingIcon = {
                if (isActive) Icon(painterResource(close),
                    "",
                    tint = nekit.corporation.common_ui.theme.accent_dark,
                    modifier = Modifier.clickable {
                        searchTextLocal.value = ""
                    })
            }, interactionSource = null, modifier = if (!isActive) Modifier.border(
                1.dp, nekit.corporation.common_ui.theme.accent_medium, RoundedCornerShape(32.dp)
            ) else Modifier

            )
        },
        expanded = isActive,
        onExpandedChange = { active ->
            onClick(active)
            Log.d("Searchbar", "onExpandedChange: $active")

        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = if (!isActive) 16.dp else 0.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(
            containerColor = if (!isActive) nekit.corporation.common_ui.theme.white else nekit.corporation.common_ui.theme.background,
            dividerColor = nekit.corporation.common_ui.theme.accent_medium,
            inputFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = nekit.corporation.common_ui.theme.accent_dark, unfocusedIndicatorColor = nekit.corporation.common_ui.theme.accent_dark
            )
        ),
    ) {
        bookList.forEach { item ->
            Spacer(Modifier.height(16.dp))
            nekit.corporation.common_ui.ui_kit.BookItem(item, onSearchItemClick, "search")
        }

    }
}


@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun Grid(
    column: Int = 3, row: Int = 2, element: List<@Composable () -> Unit> = listOf({ Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() },
        { Genre() }), modifier: Modifier = Modifier
) {
    LazyColumn {
        items(column) {
            LazyRow(
                userScrollEnabled = false, modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                items(
                    element.slice(
                        it * row until min(
                            it * (row + 1), element.size - 1
                        )
                    )
                ) { element ->
                    element()
                }
            }
        }

    }
}

@Composable
fun Genre(
    genre: GenreModel = GenreModel("id", "Какой то очень крутой жанр"),
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxHeight().clip(
        RoundedCornerShape(8.dp)
    ).background(nekit.corporation.common_ui.theme.accent_light).padding(16.dp).clickable { onClick(genre.genre) }
        .testTag("genre"), contentAlignment = Alignment.Center

    ) {
        Text(
            text = genre.genre,
            style = nekit.corporation.common_ui.theme.bookAuthorSearch,
        )
    }
}

@Composable
fun GenreLine(list: List<GenreModel>, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight().clip(
            RoundedCornerShape(8.dp)
        ).background(nekit.corporation.common_ui.theme.accent_light).padding(16.dp).clickable { onClick(list[0].genre) }
            .testTag("genre"), contentAlignment = Alignment.Center

        ) {
            Text(
                text = list[0].genre,
                style = nekit.corporation.common_ui.theme.bookAuthorSearch,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = if (list.size > 1) Modifier.weight(1f).clip(
            RoundedCornerShape(8.dp)
        ).background(nekit.corporation.common_ui.theme.accent_light).fillMaxHeight().padding(16.dp).testTag("genre")
            .clickable { onClick(list[1].genre) }
        else Modifier.weight(1f), contentAlignment = Alignment.Center) {
            if (list.size > 1) Text(
                text = list[1].genre,
                style = nekit.corporation.common_ui.theme.bookAuthorSearch,

                )
        }
    }
}

@Composable
fun RequestItem(request: RequestModel, onDelete: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clip(RoundedCornerShape(8.dp)).background(nekit.corporation.common_ui.theme.accent_light).padding(16.dp)
    ) {
        Icon(painterResource(R.drawable.time_back), "", tint = nekit.corporation.common_ui.theme.accent_dark)
        Text(
            text = request.name,
            modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically),
            style = nekit.corporation.common_ui.theme.requestText
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(close),
            "",
            modifier = Modifier.align(Alignment.CenterVertically)
                .clickable { onDelete(request.id) },
            tint = nekit.corporation.common_ui.theme.accent_dark
        )
    }
}


@Composable
fun AuthorItem(
    author: AuthorModel,
    onClick: (String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(12.dp)).background(nekit.corporation.common_ui.theme.accent_light).height(IntrinsicSize.Min)
        .testTag("author").clickable { onClick(author.name) }) {
        Image(
            author.img,
            "",
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 12.dp)
                .size(48.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            Text(text = author.name, style = nekit.corporation.common_ui.theme.authorText)
        }
    }
}
