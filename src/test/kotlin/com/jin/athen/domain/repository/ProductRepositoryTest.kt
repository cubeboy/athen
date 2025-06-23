package com.jin.athen.domain.repository

import com.jin.athen.domain.model.products.CommonCode
import com.jin.athen.domain.model.products.CommonCodeId
import com.jin.athen.domain.model.products.Product
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

// 공통 코드 테이블 조인 예제
@ExtendWith(MockKExtension::class)
@DataJpaTest
class ProductRepositoryTest @Autowired constructor (
  private val productRepository: ProductRepository,
  private val commonCodeRepository: CommonCodeRepository,
  private val entityManager: jakarta.persistence.EntityManager
) {
  private val logger = org.slf4j.LoggerFactory.getLogger(ProductRepositoryTest::class.java)

  @BeforeEach
  fun setUp() {
    // Clear the repositories before each test
    productRepository.deleteAll()
    commonCodeRepository.deleteAll()
    entityManager.flush() // Ensure all changes are flushed to the database
    entityManager.clear() // Clear the EntityManager to avoid caching issues
    commonCodeRepository.saveAll(
      listOf(
        CommonCode("CATEGORY", "ELECTRONICS", "Category", "Electronics"),
        CommonCode("CATEGORY", "BOOKS", "Category", "Books"),
        CommonCode("CATEGORY", "CLOTHING", "Category", "Clothing"),
        CommonCode("BRAND", "APPLE", "Brand", "Apple"),
        CommonCode("BRAND", "SAMSUNG", "Brand", "Samsung"),
        CommonCode("BRAND", "NIKE", "Brand", "Nike"),
      )
    )
    entityManager.flush()
    entityManager.clear() // Clear the EntityManager again to ensure no cached entities are used
    logger.info("1 =====================================================================================")
    Product(
      name = "iPhone 14",
      category = CommonCode("CATEGORY", "ELECTRONICS", "Category", "Electronics"),
      brand = CommonCode("BRAND", "APPLE", "Brand", "Apple")
    ).also { productRepository.save(it) }
    logger.info("2 =====================================================================================")
    Product(
      name = "Galaxy S21",
      category = CommonCode("CATEGORY", "ELECTRONICS", "Category", "Electronics"),
      brand = CommonCode("BRAND", "SAMSUNG", "Brand", "Samsung")
    ).also { productRepository.save(it) }
    entityManager.flush()
    entityManager.clear()
  }

  @Test
  fun `should find products by category`() {
    logger.info("3 =====================================================================================")
    // 본 쿼리 실행 전 공통 코드 캐시
    val allCodes = commonCodeRepository.findByGroupCodeIn(
      listOf(
        "CATEGORY", "BRAND"
      )
    )
    logger.info("All Common Codes: $allCodes")
    val electronics = CommonCode("CATEGORY", "ELECTRONICS", "Category", "Electronics")
    val apple = CommonCode("BRAND", "APPLE", "Brand", "Apple")
    val samsung = CommonCode("BRAND", "SAMSUNG", "Brand", "Samsung")
    val products = productRepository.findByCategory(electronics)

    assert(products.isNotEmpty()) { "Expected products in Electronics category" }
    assertEquals(2, products.size) { "Expected 2 products in Electronics category" }
    assert(products.all { it.category == electronics }) { "All products should belong to Electronics category" }
    assert(products.any { it.brand == apple }) { "Expected at least one product from Apple" }
    assert(products.any { it.brand == samsung }) { "Expected at least one product from Samsung" }
  }
}
