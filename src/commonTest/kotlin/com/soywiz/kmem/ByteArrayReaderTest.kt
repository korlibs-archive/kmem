package com.soywiz.kmem

import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayReaderTest {
    @Test
    fun test() {
        var n = 0
        buildByteArray {
            n += 1
            appendFloatBE(1f)
            appendFloatLE(2f)
        }.read {
            n += 2
            assertEquals(1f, f32BE())
            assertEquals(2f, f32LE())
        }
        assertEquals(3, n)
    }

    @Test
    fun testLE() {
        var n = 0
        buildByteArrayLE {
            n += 1
            appendFloat(1f)
            appendInt(7)
        }.readLE {
            n += 2
            assertEquals(1f, f32())
            assertEquals(7, s32())
        }
        assertEquals(3, n)
    }

    @Test
    fun testBE() {
        var n = 0
        buildByteArrayBE {
            n += 1
            appendFloat(1f)
            appendInt(7)
        }.readBE {
            n += 2
            assertEquals(1f, f32())
            assertEquals(7, s32())
        }
        assertEquals(3, n)
    }
}
