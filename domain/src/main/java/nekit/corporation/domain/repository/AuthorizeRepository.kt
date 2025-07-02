package nekit.corporation.domain.repository

import nekit.corporation.data.remote_source.dto.genre.Genres
import nekit.corporation.data.remote_source.dto.progress.SaveProgress
import nekit.corporation.data.remote_source.dto.quote.Quote
import nekit.corporation.domain.models.author.Authors
import nekit.corporation.domain.models.book.BookId
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.models.book.BooksWithAuthors
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.models.favorites.AddFavoriteRequest
import nekit.corporation.domain.models.favorites.Favorites
import nekit.corporation.domain.models.progress.Progresses
import nekit.corporation.domain.models.quote.CreateQuote
import nekit.corporation.domain.models.quote.Quotes

interface AuthorizeRepository {
    suspend fun getBooks(
        page: Int, pageSize: Int
    ): Books

    suspend fun getBooksById(id: Long): Books
    suspend fun getBooksByName(name: String): Books
    suspend fun getBooksByGenre(genre: Long): Books
    suspend fun getBooksByAuthor(authors: Long): Books
    suspend fun getNewBooks(isNew: Boolean): Books
    suspend fun getAuthors(): Authors
    suspend fun getGenres(): Genres
    suspend fun getFavorites(): Favorites
    suspend fun addFavorites(bookId: BookId): AddFavoriteRequest
    suspend fun deleteFavorites(documentBookId: String)
    suspend fun getProgress(): Progresses
    suspend fun saveProgress(progress: SaveProgress): Progresses
    suspend fun editProgress(progress: SaveProgress, bookId: String): Progresses
    suspend fun getQuotes(): Quotes
    suspend fun createQuote(createQuote: CreateQuote): Quote
    suspend fun getBooksWithAuthors(page: Int, pageSize: Int): BooksWithAuthors
    suspend fun getBookByChapterId(chapterId: Long): ChaptersWithBookAndAuthor
    suspend fun getChapterByBookId(bookId: Long): ChaptersWithBookAndAuthor
}