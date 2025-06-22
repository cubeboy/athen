package com.jin.athen.domain

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class BooleanConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean?): String {
        return if (attribute == true) "Yes" else "No"
    }

    override fun convertToEntityAttribute(dbData: String?): Boolean {
        return dbData.equals("Yes", ignoreCase = true)
    }
}
