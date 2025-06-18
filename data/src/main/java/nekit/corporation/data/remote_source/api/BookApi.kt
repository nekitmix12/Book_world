package nekit.corporation.data.remote_source.api

import nekit.corporation.data.remote_source.dto.author.AuthorsDto
import nekit.corporation.data.remote_source.dto.book.BooksDto
import nekit.corporation.data.remote_source.dto.genre.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("books")
    fun getBooks(
        @Query("pagination[page]") page: Int,
        @Query("pagination[pageSize]") pageSize: Int
    ): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("books")
    fun getBooksById(@Query("filters[id]") id: Long): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("books")
    fun getBooksByName(@Query("filters[title][\$containsi]") name: String): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("books")
    fun getBooksByGenre(@Query("filters[genres][id][\$eq]") genre: String): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("books")
    fun getBooksByAuthor(@Query("filters[authors][id][\$eq]") authors: String): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("books")
    fun getNewBooks(@Query("filters[isNew]") isNew: Boolean): nekit.corporation.data.remote_source.dto.book.BooksDto

    @GET("authors")
    fun getAuthors(): nekit.corporation.data.remote_source.dto.author.AuthorsDto

    @GET("genres")
    fun getGenres(): nekit.corporation.data.remote_source.dto.genre.GenresDto




}