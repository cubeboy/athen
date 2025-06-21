package com.jin.athen.domain.repository

import com.jin.athen.domain.model.word.Keyword
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class KeywordRepositoryTests {

  @Autowired
  private lateinit var keywordRepository: KeywordRepository

  @Test
  fun findByKeyWordContainingReturnsMatchingKeywords() {
      val keyword1 = Keyword(
          keyWord = "testKeyword1",
          codeVariable = "code1",
          databaseVariable = "db1"
      )
      val keyword2 = Keyword(
          keyWord = "testKeyword2",
          codeVariable = "code2",
          databaseVariable = "db2"
      )
      keywordRepository.save(keyword1)
      keywordRepository.save(keyword2)

      val foundKeywords = keywordRepository.findByKeyWordContaining("testKeyword")
      assertEquals(2, foundKeywords.size)
  }

  @Test
  fun findByKeyWordContainingReturnsEmptyListForNonMatchingKeyword() {
      val keyword = Keyword(
          keyWord = "uniqueKeyword",
          codeVariable = "code",
          databaseVariable = "db"
      )
      keywordRepository.save(keyword)

      val foundKeywords = keywordRepository.findByKeyWordContaining("nonMatchingKeyword")
      assertEquals(0, foundKeywords.size)
  }
}
