package com.jin.athen.domain.repository

import com.jin.athen.domain.model.products.CommonCode
import com.jin.athen.domain.model.products.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    // Define any additional query methods if needed
  fun findByCategory(category: CommonCode): List<Product>
}
