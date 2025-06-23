package com.jin.athen.domain.repository

import com.jin.athen.domain.model.books.Author
import com.jin.athen.domain.model.books.Book
import io.mockk.junit5.MockKExtension
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
@DataJpaTest
class AuthorRepositoryTest {
  private val logger = org.slf4j.LoggerFactory.getLogger(AuthorRepositoryTest::class.java)
  @Autowired
  private lateinit var repository: AuthorRepository
  @Autowired
  private lateinit var entityManager: EntityManager

  private lateinit var authors: List<Author>

  @BeforeEach
  fun setUp() {
    // Clear the repository before each test
    repository.deleteAll()
    entityManager.clear() // Clear the EntityManager to avoid caching issues
    authors = createTestAuthor()
  }

  @Test
  fun findByNameContainingReturnsMatchingAuthors() {
    val result = repository.findByNameContaining("Doe")
    entityManager.flush()
    assertEquals(2, result.size)
    assertEquals(authors[0].id, result[0].id) // Assuming ID is auto-generated and greater than 0
    assertEquals(authors[1].id, result[1].id) // Assuming ID is auto-generated and greater than 0
    assertTrue(result[0].books.isNotEmpty()) // Check if books are associated
    assertTrue(result[1].books.isNotEmpty()) // Check if books are associated
  }

  @Test
  fun removeBookFromAuthorReturnSingleBook() {
    val author = repository.findById(authors[0].id!!).orElseThrow()
    assertTrue(author.books.isNotEmpty())
    val book = author.books.first()
    author.removeBook(book)
    repository.save(author)
    entityManager.flush()
    entityManager.clear()

    val updatedAuthor = repository.findById(author.id!!).orElseThrow { Exception("Author not found") }
    assertEquals(1, updatedAuthor.books.count())
  }

  @Test
  fun removeAllBooksFromAuthorReturnEmptyBooks() {
    val author = repository.findById(authors[0].id!!).orElseThrow()
    assertTrue(author.books.isNotEmpty())
    author.removeBooks()
    repository.save(author)
    entityManager.flush()
    entityManager.clear()

    val updatedAuthor = repository.findById(author.id!!).orElseThrow { Exception("Author not found") }
    assertTrue(updatedAuthor.books.isEmpty())
  }

  @Test
  fun findByNameContainingReturnsEmptyListForNoMatches() {
    val result = repository.findByNameContaining("Smith")
    assertTrue(result.isEmpty())
  }

  @Test
  fun findAllReturnsAllAuthorsWithBooksFetched() {
    val result = repository.findAll(PageRequest.of(0, 10))
    entityManager.flush()
    assertEquals(1, result.totalPages)
    assertEquals(0, result.pageable.pageNumber)
    assertEquals(2, result.content.size)
    assertTrue(result.content.all { it.books.isNotEmpty() })
  }

  @Test
  fun findAllReturnsOutOfPageEmptyContent() {
    val result = repository.findAll(PageRequest.of(1, 10))
    entityManager.flush()
    assertEquals(1, result.totalPages)
    assertEquals(1, result.pageable.pageNumber)
    assertTrue(result.content.isEmpty())
  }

  private fun createTestAuthor(): List<Author> {
    val author1 = Author(age = 45, genre = "Fiction", name = "John Doe")
    val author2 = Author(age = 50, genre = "Non-Fiction", name = "Jane Doe")
    author1.addBook(
      Book(
        title = "Book One",
        isbn = "ISBN-1234567890")
    )
    author1.addBook(
      Book(
        title = "Book Two",
        isbn = "ISBN-2345678901")
    )
    author2.addBook(
      Book(
        title = "Book Two",
        isbn = "ISBN-0987654321")
    )
    repository.save(author1)
    repository.save(author2)
//    entityManager.flush()
//    entityManager.clear()
    return listOf(author1, author2)
  }
}
