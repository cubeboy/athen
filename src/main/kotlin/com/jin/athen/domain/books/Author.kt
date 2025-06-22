package com.jin.athen.domain.books

import jakarta.persistence.*

@Entity
// entity class 는 data class 를 사용하지 않음
@NamedEntityGraph(
  name = "Author.books",
  attributeNodes = [
    NamedAttributeNode("books")
  ]
)
class Author(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  var age: Int,

  var genre: String,

  var name: String,

  // one to many 관계에서 fetchType.LAZY 는 기본값
  @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
//  @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
  val books: List<Book> = mutableListOf()
) {
  fun addBook(book: Book) {
    book.author = this
    (books as MutableList).add(book)
  }

  fun removeBook(book: Book) {
    book.author = null
    (books as MutableList).remove(book)
  }

  fun removeBooks() {
    books.forEach { it.author = null }
    (books as MutableList).clear()
  }

  // toString, equals, ,hashCode 는 성능 최적화를 위해 반드시 오버라이드
  override fun toString(): String {
    return "Author(id=$id, age=$age, genre='$genre', name='$name')"
  }
  override fun equals(other: Any?): Boolean {
    if (id == null) return false
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Author

    return id == other.id
  }
  override fun hashCode(): Int {
    return 62111220
  }
}
