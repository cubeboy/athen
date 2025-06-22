package com.jin.athen.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BooleanConverterTest {

    private val converter = BooleanConverter()

    @Test
    fun `convertToDatabaseColumn should return Yes for true`() {
        val result = converter.convertToDatabaseColumn(true)
        assertEquals("Yes", result)
    }

    @Test
    fun `convertToDatabaseColumn should return No for false`() {
        val result = converter.convertToDatabaseColumn(false)
        assertEquals("No", result)
    }

    @Test
    fun `convertToDatabaseColumn should return No for null`() {
        val result = converter.convertToDatabaseColumn(null)
        assertEquals("No", result)
    }

    @Test
    fun `convertToEntityAttribute should return true for Yes`() {
        val result = converter.convertToEntityAttribute("Yes")
        assertTrue(result)
    }

    @Test
    fun `convertToEntityAttribute should return false for No`() {
        val result = converter.convertToEntityAttribute("No")
        assertFalse(result)
    }

    @Test
    fun `convertToEntityAttribute should return false for null`() {
        val result = converter.convertToEntityAttribute(null)
        assertFalse(result)
    }

    @Test
    fun `convertToEntityAttribute should return false for invalid value`() {
        val result = converter.convertToEntityAttribute("Invalid")
        assertFalse(result)
    }
}
