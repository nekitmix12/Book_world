package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.author.AuthorsDto
import nekit.corporation.data.remote_source.dto.book.BooksDto
import nekit.corporation.data.remote_source.dto.genre.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("books")
    suspend fun getBooks(
        @Query("pagination[page]") page: Int,
        @Query("pagination[pageSize]") pageSize: Int
    ): BooksDto

    @GET("books")
    suspend fun getBooksById(@Query("filters[id]") id: Long): BooksDto

    @GET("books")
    suspend fun getBooksByName(@Query("filters[title][\$containsi]") name: String): BooksDto

    @GET("books")
    suspend fun getBooksByGenre(@Query("filters[genres][id][\$eq]") genre: String): BooksDto

    @GET("books")
    suspend fun getBooksByAuthor(@Query("filters[authors][id][\$eq]") authors: String): BooksDto

    @GET("books")
    suspend fun getNewBooks(@Query("filters[isNew]") isNew: Boolean): BooksDto

    @GET("authors")
    suspend fun getAuthors(): AuthorsDto

    @GET("genres")
    suspend fun getGenres(): GenresDto


}