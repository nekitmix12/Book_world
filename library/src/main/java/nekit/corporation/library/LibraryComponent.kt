package nekit.corporation.library

import kotlinx.coroutines.flow.StateFlow

interface LibraryComponent {
    var state: StateFlow<LibraryState>

    fun onBookClick(bookId: String)
}