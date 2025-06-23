package com.jin.athen.domain.repository

import com.jin.athen.domain.model.shopping.ShoppingCart
import com.jin.athen.domain.model.shopping.ShoppingItem
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class ShoppingCartRepositoryTest @Autowired constructor(
  private val repository: ShoppingCartRepository,
  private val entityManager: EntityManager
) {
  private val logger = org.slf4j.LoggerFactory.getLogger(ShoppingCartRepositoryTest::class.java)
  private lateinit var shoppingCart: ShoppingCart

  @BeforeEach
  fun setUp() {
    // Clear the repository before each test
    repository.deleteAll()
    repository.flush()
    entityManager.clear() // Clear the EntityManager to avoid caching issues
    val item1 = ShoppingItem(
      category = "Electronics",
      name = "Smartphone",
      price = 699.99,
      discountPrice = 599.99,
      discount = true,
      discountRate = 0.15
    )
    val item2 = ShoppingItem(
      category = "Books",
      name = "Kotlin Programming",
      price = 29.99,
      discountPrice = 19.99,
      discount = true,
      discountRate = 0.33
    )

    val shoppingCart = ShoppingCart(
      userId = "testUser",
      expireDate = LocalDateTime.now().plusDays(1)
    )
    shoppingCart.addItem(item1)
    shoppingCart.addItem(item2)
    repository.save(shoppingCart)
    this.shoppingCart = shoppingCart
  }
  @Test
  fun `should save and retrieve ShoppingCart`() {
    val retrievedCart = repository.findByUserId("testUser")
    assertNotNull(retrievedCart)
    assertTrue(retrievedCart?.id!! > 0)
    assertEquals(2, retrievedCart.items.size)
    assertEquals("testUser", retrievedCart?.userId)
  }

  @Test
  fun `should delete ShoppingCart by userId`() {
    repository.deleteByUserId("testUser")
    val retrievedCart = repository.findByUserId("testUser")
    assertNull(retrievedCart)
  }

  @Test
  fun `should remove all items from ShoppingCart`() {
    shoppingCart.removeItems()
    repository.save(shoppingCart)
    entityManager.flush()
    entityManager.clear()

    val updatedCart = repository.findById(shoppingCart.id!!).orElseThrow { Exception("ShoppingCart not found") }
    assertEquals(0, updatedCart.items.size, "Items should be removed from the cart")
  }

  @Test
  fun `should remove specific item from ShoppingCart`() {
    val itemToRemove = shoppingCart.items.first()
    shoppingCart.removeItem(itemToRemove)
    repository.save(shoppingCart)
    entityManager.flush()
    entityManager.clear()

    val updatedCart = repository.findById(shoppingCart.id!!).orElseThrow { Exception("ShoppingCart not found") }
    assertFalse(updatedCart.items.contains(itemToRemove), "Item should be removed from the cart")
    assertEquals(1, updatedCart.items.size, "Only one item should remain in the cart")
  }
}
