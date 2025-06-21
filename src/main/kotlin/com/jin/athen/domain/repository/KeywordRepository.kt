package com.jin.athen.domain.repository

import com.jin.athen.domain.model.word.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByKeyWordContaining(keyWord: String): List<Keyword>
}
