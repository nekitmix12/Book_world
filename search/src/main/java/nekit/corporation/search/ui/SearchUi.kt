package nekit.corporation.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import nekit.corporation.common.models.BookSearchModel
import nekit.corporation.common_ui.R.drawable
import nekit.corporation.common_ui.R.drawable.close
import nekit.corporation.common_ui.theme.BookWorldTheme
import nekit.corporation.common_ui.theme.accent_dark
import nekit.corporation.common_ui.theme.accent_medium
import nekit.corporation.common_ui.theme.background
import nekit.corporation.common_ui.theme.textBarText
import nekit.corporation.common_ui.theme.white
import nekit.corporation.common_ui.ui_kit.BookItem
import nekit.corporation.common_ui.ui_kit.Grid
import nekit.corporation.common_ui.ui_kit.MiddleLabel
import nekit.corporation.search.R
import nekit.corporation.search.SearchComponent
import nekit.corporation.search.SearchState
import nekit.corporation.search.models.GenreModel
import nekit.corporation.search.models.RequestModel

@Composable
fun SearchUi(component: SearchComponent) {
    val state by component.state.collectAsState()
    if (state.isLoading) {

    }
    Column(
        Modifier
            .background(background)
            .fillMaxSize()
            .testTag("search screen")
    ) {
        SearchPanel(
            bookList = state.books ?: persistentListOf(),
            isActive = state.isSearchActive,
            query = state.searchText,
            onQueryChange = component::onQueryChange,
            onClick = component::onSearchClick,
            onSearch = component::onSearchByText,
            onSearchItemClick = component::onBookClick,
        )
        LazyColumn {
            item { Spacer(Modifier.height(24.dp)) }
            item { MiddleLabel(stringResource(R.string.last_request)) }
            item { Spacer(Modifier.height(8.dp)) }
            items(state.requests ?: persistentListOf()) {
                RequestItem(it, component::onRequestClose, component::onRequestClick)
            }
            item { Spacer(Modifier.height(24.dp)) }
            item { MiddleLabel(stringResource(R.string.genres)) }
            item {
                Grid(
                    column = 2,
                    row = ((state.genres?.size) ?: 0) / 2,
                    (state.genres
                        ?: persistentListOf()).map<GenreModel, @Composable (Modifier) -> Unit> { model ->
                        @Composable { m: Modifier ->
                            Genre(
                                genre = model, modifier = m, onClick = component::onGenreClick
                            )
                        }
                    }.toPersistentList(),
                    elementModifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 4.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            item { Spacer(Modifier.height(24.dp)) }
            item { MiddleLabel(stringResource(R.string.authors)) }
            items(state.authors?.size ?: 0) { item ->
                AuthorItem(state.authors!![item], component::setSearchText)
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanel(
    modifier: Modifier = Modifier,
    bookList: List<BookSearchModel>,
    isActive: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onClick: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onSearchItemClick: (Long) -> Unit,

    ) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false, onExpandedChange = { active ->
                    onClick(active)
                }, enabled = true, colors = TextFieldDefaults.colors(
                    focusedTextColor = accent_dark,
                    unfocusedTextColor = accent_dark,
                    focusedIndicatorColor = accent_dark,
                    unfocusedIndicatorColor = accent_dark
                ), placeholder = {
                    Text(text = stringResource(R.string.search_by_book), style = textBarText)
                }, leadingIcon = {
                    if (!isActive) Icon(
                        painterResource(R.drawable.find), "", tint = accent_medium
                    )
                    else Icon(
                        painterResource(
                            drawable.back
                        ), "", tint = accent_dark, modifier = Modifier.clickable {
                            onClick(false)
                        })
                }, trailingIcon = {
                    if (isActive) Icon(
                        painterResource(close),
                        "",
                        tint = accent_dark,
                        modifier = Modifier.clickable {
                            onQueryChange("")
                        })
                }, interactionSource = null, modifier = if (!isActive) Modifier.border(
                    1.dp, accent_medium, RoundedCornerShape(32.dp)
                ) else Modifier

            )
        },
        expanded = isActive,
        onExpandedChange = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = if (!isActive) 16.dp else 0.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(
            containerColor = if (!isActive) white else background,
            dividerColor = accent_medium,
            inputFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = accent_dark, unfocusedIndicatorColor = accent_dark
            )
        ),
        windowInsets = WindowInsets(top = 0.dp)
    ) {
        bookList.forEach { item ->
            Spacer(Modifier.height(16.dp))
            BookItem(item, onSearchItemClick, "search")
        }

    }
}


class FakeSearchComponent : SearchComponent {
    override val state = MutableStateFlow(
        SearchState(
            requests = persistentListOf(RequestModel("", "IOS")),
            genres = persistentListOf(
                GenreModel(1, "Classic"),
                GenreModel(1, "Fantasy"),
                GenreModel(1, "Fantastic"),
                GenreModel(1, "Detective"),
                GenreModel(1, "Thriller"),
                GenreModel(1, "History roman"),
                GenreModel(1, "Love story"),
                GenreModel(1, "Adventures"),
                GenreModel(1, "Poems"),
                GenreModel(1, "Biography"),
                GenreModel(1, "For guys"),
                GenreModel(1, "For children"),
            ),
            authors = persistentListOf(),
            searchIsOpen = false,
            searchText = "IOS",
            isLoading = false,
            isSearching = false,
            books = persistentListOf(
            ),
            isSearchActive = false
        )
    )

    override fun onAuthorClick(authorId: Long) {
        TODO("Not yet implemented")
    }

    override fun onGenreClick(genreId: Long) {
        TODO("Not yet implemented")
    }

    override fun onRequestClick(requestId: String) {
        TODO("Not yet implemented")
    }


    override fun onRequestClose(requestId: String) {
        TODO("Not yet implemented")
    }

    override fun onSearchByText(text: String) {
        TODO("Not yet implemented")
    }

    override fun onQueryChange(query: String) {
        TODO("Not yet implemented")
    }


    override fun onSearchClick(isActive: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onBookClick(bookId: Long) {
        TODO("Not yet implemented")
    }

    override fun setSearchText(text: String) {
        TODO("Not yet implemented")
    }

    @Preview(showSystemUi = true, device = Devices.PIXEL_6, showBackground = true)
    @Composable
    fun PreviewSearchUi() {
        BookWorldTheme {
            SearchUi(this)
        }
    }
}

