package com.jin.athen.domain.repository

import com.jin.athen.domain.model.products.CommonCodeId
import com.jin.athen.domain.model.products.CommonCode
import org.springframework.data.jpa.repository.JpaRepository

interface CommonCodeRepository : JpaRepository<CommonCode, CommonCodeId> {
  fun findByGroupCodeIn(groupCodes: List<String>): List<CommonCode>
}
