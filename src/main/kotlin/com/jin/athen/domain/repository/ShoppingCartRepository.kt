package com.jin.athen.domain.repository

import com.jin.athen.domain.shopping.ShoppingCart
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingCartRepository : JpaRepository<ShoppingCart, Long>
{
    fun findByUserId(userId: String): ShoppingCart?
    fun deleteByUserId(userId: String)
}
