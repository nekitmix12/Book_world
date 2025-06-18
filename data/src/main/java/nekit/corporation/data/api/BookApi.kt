package nekit.corporation.data.api

import nekit.corporation.data.dto.author.AuthorsDto
import nekit.corporation.data.dto.book.BooksDto
import nekit.corporation.data.dto.genre.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BookApi {

    @GET("books")
    fun getBooks(
        @Query("pagination[page]") page: Int,
        @Query("pagination[pageSize]") pageSize: Int
    ): BooksDto

    @GET("books")
    fun getBooksById(@Query("filters[id]") id: Long): BooksDto

    @GET("books")
    fun getBooksByName(@Query("filters[title][\$containsi]") name: String): BooksDto

    @GET("books")
    fun getBooksByGenre(@Query("filters[genres][id][\$eq]") genre: String): BooksDto

    @GET("books")
    fun getBooksByAuthor(@Query("filters[authors][id][\$eq]") authors: String): BooksDto

    @GET("books")
    fun getNewBooks(@Query("filters[isNew]") isNew: Boolean): BooksDto

    @GET("authors")
    fun getAuthors(): AuthorsDto

    @GET("genres")
    fun getGenres(): GenresDto




}