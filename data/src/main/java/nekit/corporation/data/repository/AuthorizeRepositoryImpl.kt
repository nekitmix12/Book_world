package nekit.corporation.data.repository

import com.squareup.anvil.annotations.ContributesBinding
import nekit.corporation.common.AppScope
import nekit.corporation.data.mappers.toAddFavoriteRequest
import nekit.corporation.data.mappers.toAuthors
import nekit.corporation.data.mappers.toBooks
import nekit.corporation.data.mappers.toFavorites
import nekit.corporation.data.mappers.toGenresDto
import nekit.corporation.data.mappers.toProgresses
import nekit.corporation.data.mappers.toQuote
import nekit.corporation.data.mappers.toQuotes
import nekit.corporation.data.mappers.toRefreshRequestDto
import nekit.corporation.data.mappers.toSaveProgressDto
import nekit.corporation.data.mappers.toTokenResponse
import nekit.corporation.data.remote_source.api.AuthApi
import nekit.corporation.data.remote_source.api.BookApi
import nekit.corporation.data.remote_source.api.FavoritesApi
import nekit.corporation.data.remote_source.api.ProgressApi
import nekit.corporation.data.remote_source.api.QuotesApi
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
import nekit.corporation.domain.repository.AuthorizeRepository
import javax.inject.Inject

@ContributesBinding(
    AppScope::class,
    boundType = AuthorizeRepository::class
)
class AuthorizeRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val favoritesApi: FavoritesApi,
    private val progressApi: ProgressApi,
    private val quotesApi: QuotesApi,
    private val authApi: AuthApi
) : AuthorizeRepository {


    override suspend fun getBooks(page: Int, pageSize: Int): Books =
        bookApi.getBooks(page, pageSize).toBooks()

    override suspend fun getBooksById(id: Long): Books =
        bookApi.getBooksById(id).toBooks()

    override suspend fun getBooksByName(name: String): Books =
        bookApi.getBooksByName(name).toBooks()

    override suspend fun getBooksByGenre(genre: String): Books =
        bookApi.getBooksByGenre(genre).toBooks()

    override suspend fun getBooksByAuthor(authors: String): Books =
        bookApi.getBooksByAuthor(authors).toBooks()

    override suspend fun getNewBooks(isNew: Boolean): Books =
        bookApi.getNewBooks(isNew).toBooks()

    override suspend fun getAuthors(): Authors =
        bookApi.getAuthors().toAuthors()

    override suspend fun getGenres(): Genres =
        bookApi.getGenres().toGenresDto()

    override suspend fun getFavorites(): Favorites =
        favoritesApi.getFavorites().toFavorites()

    override suspend fun addFavorites(): AddFavoriteRequest =
        favoritesApi.addFavorites().toAddFavoriteRequest()

    override suspend fun deleteFavorites() =
        favoritesApi.deleteFavorites()

    override suspend fun getProgress(): Progresses =
        progressApi.getProgress().toProgresses()

    override suspend fun saveProgress(progress: SaveProgress): Progresses =
        progressApi.saveProgress(progress.toSaveProgressDto()).toProgresses()

    override suspend fun editProgress(progress: SaveProgress, bookId: String): Progresses =
        progressApi.editProgress(progress.toSaveProgressDto(), bookId).toProgresses()

    override suspend fun getQuotes(): Quotes =
        quotesApi.getQuotes().toQuotes()

    override suspend fun createQuote(createQuote: CreateQuote): Quote =
        quotesApi.createQuote(createQuote).toQuote()
}