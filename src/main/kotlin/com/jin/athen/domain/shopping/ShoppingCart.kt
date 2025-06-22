package com.jin.athen.domain.shopping

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ShoppingCart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: String,

    var expireDate: LocalDateTime,

    // 다중 연관계에서는 List 보다 Set 을 사용하는 것이 성능상 유리
    // Many to Many 관계에서 fetch 는 LAZY 가 기본값
    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @OrderBy("category DESC, name ASC")
    @JoinTable(
        name = "shopping_cart_item",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableSet<ShoppingItem> = mutableSetOf()
) {
  fun addItem(item: ShoppingItem) {
    item.carts.add(this)
    items.add(item)
  }
  fun removeItem(item: ShoppingItem) {
    item.carts.remove(this)
    items.remove(item)
  }
  fun removeItems() {
    items.forEach { it.carts.remove(this) }
    items.clear()
  }
  override fun toString(): String {
    return "ShoppingCart(id=$id, userId='$userId', expireDate=$expireDate)"
  }

  override fun equals(other: Any?): Boolean {
    if (id == null) return false
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ShoppingCart

    return id == other.id
  }

  // 고유한 해시코드를 사용하여 ShoppingCart 객체의 동등성을 보장
  override fun hashCode(): Int {
    return 62111235
  }
}
