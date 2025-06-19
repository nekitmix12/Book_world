package nekit.corporation.domain.repository

import nekit.corporation.data.remote_source.dto.favorites.Favorites
import nekit.corporation.data.remote_source.dto.genre.Genres
import nekit.corporation.data.remote_source.dto.progress.Progresses
import nekit.corporation.data.remote_source.dto.progress.SaveProgress
import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.domain.models.auth.RefreshRequest
import nekit.corporation.domain.models.auth.TokenResponse
import nekit.corporation.domain.models.author.Authors
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.models.favorites.AddFavoriteRequest
import nekit.corporation.domain.models.quote.CreateQuote
import nekit.corporation.domain.models.quote.Quotes

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