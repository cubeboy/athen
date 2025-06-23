package com.jin.athen.domain.repository

import com.jin.athen.domain.model.books.Author
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByNameContaining(name: String): List<Author>
    @EntityGraph(value = "Author.books", type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(pageable: Pageable): Page<Author> // JPARepository 의 기본 메서드 오버라이드
}
