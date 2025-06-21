package com.jin.athen.domain.books

import jakarta.persistence.*

@Entity
class Book(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  var isbn: String,

  var title: String,

  // many to one 관계에서 fetchType.EAGER 는 기본값
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  var author: Author? = null
) {
  override fun toString(): String {
    return "Book(id=$id, isbn='$isbn', title='$title')"
  }

  override fun equals(other: Any?): Boolean {
    if (id == null) return false
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Book

    return id == other.id
  }

  override fun hashCode(): Int {
    return 62111225
  }
}
