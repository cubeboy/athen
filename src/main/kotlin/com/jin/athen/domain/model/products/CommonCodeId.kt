package com.jin.athen.domain.model.products

import java.io.Serializable

class CommonCodeId(
  var groupCode: String? = null,
  var commonCode: String? = null
) : Serializable {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as CommonCodeId
    return groupCode == other.groupCode && commonCode == other.commonCode
  }

  override fun hashCode(): Int {
    return groupCode.hashCode() * 31 + commonCode.hashCode()
  }
}
