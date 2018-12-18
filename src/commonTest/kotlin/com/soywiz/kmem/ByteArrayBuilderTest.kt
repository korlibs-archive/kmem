package com.soywiz.kmem

import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayBuilderTest {
    @Test
    fun test() {
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 0, 0, 0),
            buildByteArray {
                append(1)
                append(2)
                append(byteArrayOf(3, 4, 5))
                appendIntLE(6)
            }.apply {
                assertEquals(9, size)
            }.map { it.toInt() }
        )
    }
}