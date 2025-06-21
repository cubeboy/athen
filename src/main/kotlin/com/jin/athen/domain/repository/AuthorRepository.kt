package com.jin.athen.domain.repository

import com.jin.athen.domain.books.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByNameContaining(name: String): List<Author>
}
