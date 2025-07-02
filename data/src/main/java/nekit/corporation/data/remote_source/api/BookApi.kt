package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.author.AuthorsDto
import nekit.corporation.data.remote_source.dto.book.BooksDto
import nekit.corporation.data.remote_source.dto.book.BooksWithAuthorDto
import nekit.corporation.data.remote_source.dto.book.ChaptersWithBookAndAuthorDto
import nekit.corporation.data.remote_source.dto.genre.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("api/books")
    suspend fun getBooks(
        @Query("pagination[page]") page: Int,
        @Query("pagination[pageSize]") pageSize: Int,
        @Query("populate[0]") authors: String = "authors"
    ): BooksDto

    @GET("api/books")
    suspend fun getBooksById(
        @Query("filters[id]") id: Long,
        @Query("populate[0]") authors: String = "authors"
    ): BooksDto

    @GET("api/books")
    suspend fun getBooksByName(
        @Query("filters[title][\$containsi]") name: String,
        @Query("populate[0]") authors: String = "authors"
    ): BooksDto

    @GET("api/books")
    suspend fun getBooksByGenre(
        @Query("filters[genres][id][\$eq]") genre: Long,
        @Query("populate[0]") authors: String = "authors"
    ): BooksDto

    @GET("api/books")
    suspend fun getBooksByAuthor(
        @Query("filters[authors][id][\$eq]") authors: Long,
        @Query("populate[0]") author: String = "authors"
    ): BooksDto

    @GET("api/books")
    suspend fun getNewBooks(@Query("filters[isNew]") isNew: Boolean): BooksDto

    @GET("api/authors")
    suspend fun getAuthors(): AuthorsDto

    @GET("api/genres")
    suspend fun getGenres(): GenresDto

    @GET("api/chapters")
    suspend fun getBookByChapterId(
        @Query("filters[id][\$eq]") chapterId: Long,
        @Query("populate[book][populate][authors][fields][0]") id: String = "id",
        @Query("populate[book][populate][authors][fields][1]") name: String = "name",
        @Query("populate[book][populate][authors][fields][2]") avatarURL: String = "avatarURL",
    ): ChaptersWithBookAndAuthorDto

    @GET("api/chapters")
    suspend fun getChapterByBookId(
        @Query("filters[book][id][\$eq]") bookId: Long,
        @Query("populate[book][populate][authors][fields][0]") id: String = "id",
        @Query("populate[book][populate][authors][fields][1]") name: String = "name",
        @Query("populate[book][populate][authors][fields][2]") avatarURL: String = "avatarURL",
    ): ChaptersWithBookAndAuthorDto

    @GET("api/books")
    suspend fun getBooksWithAuthors(
        @Query("pagination[page]") page: Int,
        @Query("pagination[pageSize]") pageSize: Int,
        @Query("populate[0]") authors: String = "authors"
    ): BooksWithAuthorDto


}