package com.jin.athen.domain.model.products

import jakarta.persistence.*

@Entity
@IdClass(CommonCodeId::class)
class CommonCode(
    @Id
    var groupCode: String,
    @Id
    var commonCode: String,
    var groupName: String,
    var codeName: String
) {
    override fun toString(): String {
        return """CommonCode(
          |groupCode='$groupCode',
          |commonCode='$commonCode',
          |groupName='$groupName',
          |codeName='$codeName')""".trimMargin()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CommonCode
        return groupCode == other.groupCode && commonCode == other.commonCode
    }

    override fun hashCode(): Int {
        return groupCode.hashCode() * 31 + commonCode.hashCode()
    }
}
