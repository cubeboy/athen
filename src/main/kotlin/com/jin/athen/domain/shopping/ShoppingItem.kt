package com.jin.athen.domain.shopping

import jakarta.persistence.*

@Entity
class ShoppingItem(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  var category: String,
  var name: String,
  var price: Double,
  var discountPrice: Double,
  var discount: Boolean,
  var discountRate: Double,

  // 다중 연관계에서는 List 보다 Set 을 사용하는 것이 성능상 유리
  @ManyToMany(mappedBy = "items")
  val carts: MutableSet<ShoppingCart> = mutableSetOf()
) {
  override fun toString(): String {
    return "ShoppingItem(id=$id, category='$category', name='$name', price=$price, discountPrice=$discountPrice, discount=$discount, discountRate=$discountRate)"
  }

  override fun equals(other: Any?): Boolean {
    if (id == null) return false
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as ShoppingItem
    return id == other.id
  }

  override fun hashCode(): Int {
    return 62111230
  }
}
