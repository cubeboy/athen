package com.jin.athen.domain.shopping

import com.jin.athen.domain.BooleanConverter
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
  // Boolean 타입을 String 으로 변환하여 저장
  @Convert(converter = BooleanConverter::class)
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
