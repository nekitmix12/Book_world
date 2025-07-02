package nekit.corporation.data.mappers

import nekit.corporation.data.remote_source.dto.author.Author
import nekit.corporation.data.remote_source.dto.author.AuthorDto
import nekit.corporation.data.remote_source.dto.author.AuthorsDto
import nekit.corporation.data.remote_source.dto.book.BookDto
import nekit.corporation.data.remote_source.dto.book.BookWithAuthorDto
import nekit.corporation.data.remote_source.dto.book.BookWithShortAuthorsDto
import nekit.corporation.data.remote_source.dto.book.BooksDto
import nekit.corporation.data.remote_source.dto.book.BooksWithAuthorDto
import nekit.corporation.data.remote_source.dto.book.ChapterWithBookAndAuthorDto
import nekit.corporation.data.remote_source.dto.book.ChaptersWithBookAndAuthorDto
import nekit.corporation.data.remote_source.dto.book.ShortAuthorDto
import nekit.corporation.data.remote_source.dto.genre.Genre
import nekit.corporation.data.remote_source.dto.genre.GenreDto
import nekit.corporation.data.remote_source.dto.genre.Genres
import nekit.corporation.data.remote_source.dto.genre.GenresDto
import nekit.corporation.domain.models.author.Authors
import nekit.corporation.domain.models.book.Book
import nekit.corporation.domain.models.book.BookWithAuthor
import nekit.corporation.domain.models.book.BookWithShortAuthors
import nekit.corporation.domain.models.book.Books
import nekit.corporation.domain.models.book.BooksWithAuthors
import nekit.corporation.domain.models.book.ChapterWithBookAndAuthor
import nekit.corporation.domain.models.book.ChaptersWithBookAndAuthor
import nekit.corporation.domain.models.book.ShortAuthor


fun BooksDto.toBooks() = Books(data = data.map { it.toBook() }, meta = meta.toMeta())


fun BookDto.toBook() = Book(
    id = id,
    documentId = documentId,
    title = title,
    coverURL = coverURL,
    createdAt = createdAt,
    updatedAt = updatedAt,
    publishedAt = publishedAt,
    isNew = isNew,
    illustrationURL = illustrationURL,
    description = description,
    author = authors.map { it.toAuthor() }
)


fun BooksWithAuthorDto.toBooksWithAuthors() =
    BooksWithAuthors(data.map { it.toBookWithAuthors() }, meta.toMeta())

fun BookWithAuthorDto.toBookWithAuthors() = BookWithAuthor(
    id,
    documentId,
    title,
    coverURL,
    createdAt,
    updatedAt,
    publishedAt,
    isNew,
    illustrationURL,
    description,
    authors.map { it.toAuthor() })

fun AuthorsDto.toAuthors() = Authors(data = data.map { it.toAuthor() }, meta = meta.toMeta())

fun AuthorDto.toAuthor() = Author(id, documentId, name, createdAt, updatedAt, publishedAt)

fun GenreDto.toGenreDto() = Genre(id, documentId, name, createdAt, updatedAt, publishedAt)

fun GenresDto.toGenresDto() = Genres(data.map { it.toGenreDto() }, meta.toMeta())

fun ChaptersWithBookAndAuthorDto.toChaptersWithBookAndAuthor() =
    ChaptersWithBookAndAuthor(data.map { it.toChapterWithBookAndAuthor() }, meta.toMeta())

fun ChapterWithBookAndAuthorDto.toChapterWithBookAndAuthor() = ChapterWithBookAndAuthor(
    id,
    documentId,
    text,
    title,
    order,
    createdAt,
    updatedAt,
    publishedAt,
    book.toBookWithShortAuthors()
)

fun BookWithShortAuthorsDto.toBookWithShortAuthors() = BookWithShortAuthors(
    id,
    documentId,
    title,
    coverURL,
    createdAt,
    updatedAt,
    publishedAt,
    isNew,
    illustrationURL,
    description,
    authors.map { it.toShortAuthor() })

fun ShortAuthorDto.toShortAuthor() = ShortAuthor(id, documentId, name, avatarURL)