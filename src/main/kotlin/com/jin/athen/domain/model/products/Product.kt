package com.jin.athen.domain.model.products

import jakarta.persistence.*

@Entity
class Product(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumns(
    JoinColumn(name = "CategoryGroupCode", referencedColumnName = "groupCode"),
    JoinColumn(name = "CategoryCommonCode", referencedColumnName = "commonCode"))
  var category: CommonCode? = null,
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumns(
    JoinColumn(name = "BrandGroupCode", referencedColumnName = "groupCode"),
    JoinColumn(name = "BrandCommonCode", referencedColumnName = "commonCode"))
  var brand: CommonCode? = null,
  var name: String
) {
  override fun toString(): String {
    return "Product(id=$id, name='$name')"
  }

  override fun equals(other: Any?): Boolean {
    if (id == null) return false
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Product
    return id == other.id
  }

  override fun hashCode(): Int {
    return 62111240
  }
}
