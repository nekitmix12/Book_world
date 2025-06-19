package nekit.corporation.domain.repository

import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.TokenResponse

interface AuthorizeRepository {
    suspend fun refreshToken(refreshRequest: RefreshRequest): TokenResponse
    suspend fun getBooks(
        page: Int,
        pageSize: Int
    ): Books

    suspend fun getBooksById(id: Long): Books
    suspend fun getBooksByName(name: String): Books
    suspend fun getBooksByGenre(genre: String): Books
    suspend fun getBooksByAuthor(authors: String): Books
    suspend fun getNewBooks(isNew: Boolean): Books
    suspend fun getAuthors(): Authors
    suspend fun getGenres(): Genres
    suspend fun getFavorites(): Favorites
    suspend fun addFavorites(): AddFavoriteRequest
    suspend fun deleteFavorites()
    suspend fun getProgress(): Progresses
    suspend fun saveProgress(progress: SaveProgress): Progresses
    suspend fun editProgress(progress: SaveProgress,  bookId: String): Progresses
    suspend fun getQuotes(): Quotes
    suspend fun createQuote(createQuote: CreateQuote): Quote

}