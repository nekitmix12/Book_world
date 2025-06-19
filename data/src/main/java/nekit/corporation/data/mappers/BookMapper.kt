package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.author.Author
import nekit.corporation.data.remote_source.dto.author.AuthorDto
import nekit.corporation.data.remote_source.dto.author.AuthorsDto
import nekit.corporation.data.remote_source.dto.book.BookDto
import nekit.corporation.data.remote_source.dto.book.BooksDto
import nekit.corporation.data.remote_source.dto.genre.Genre
import nekit.corporation.data.remote_source.dto.genre.GenreDto
import nekit.corporation.data.remote_source.dto.genre.Genres
import nekit.corporation.data.remote_source.dto.genre.GenresDto
import nekit.corporation.domain.models.author.Authors
import nekit.corporation.domain.models.book.Book
import nekit.corporation.domain.models.book.Books

fun Books.toBooksDto() = BooksDto(data.map { it.toBookDto() }, meta.toMetaDto())

fun BooksDto.toBooks() = Books(data = data.map { it.toBook() }, meta = meta.toMeta())

fun Book.toBookDto() = BookDto(
    id = id,
    documentId = documentId,
    title = title,
    coverURL = coverURL,
    createdAt = createdAt,
    updatedAt = updatedAt,
    publishedAt = publishedAt,
    isNew = isNew,
    illustrationURL = illustrationURL
)

fun BookDto.toBook() = Book(
    id = id,
    documentId = documentId,
    title = title,
    coverURL = coverURL,
    createdAt = createdAt,
    updatedAt = updatedAt,
    publishedAt = publishedAt,
    isNew = isNew,
    illustrationURL = illustrationURL
)

fun AuthorsDto.toAuthors() = Authors(data = data.map { it.toAuthor() }, meta = meta.toMeta())

fun AuthorDto.toAuthor() = Author(id, documentId, name, createdAt, updatedAt, publishedAt)

fun GenreDto.toGenreDto() = Genre(id, documentId, name, createdAt, updatedAt, publishedAt)

fun GenresDto.toGenresDto() = Genres(data.map { it.toGenreDto() }, meta.toMeta())