package com.soywiz.kmem.numeric

import com.soywiz.kmem.isPowerOfTwo
import com.soywiz.kmem.nextPowerOfTwo
import com.soywiz.kmem.prevPowerOfTwo
import kotlin.test.Test
import kotlin.test.assertEquals

class PowerOfTwoTest {
    @Test
    fun isPowerOfTwo() {
        assertEquals(true, 0.isPowerOfTwo)
        assertEquals(true, 1.isPowerOfTwo)
        assertEquals(true, 2.isPowerOfTwo)
        assertEquals(false, 3.isPowerOfTwo)
        assertEquals(true, 4.isPowerOfTwo)
        assertEquals(false, 5.isPowerOfTwo)

        assertEquals(false, 1023.isPowerOfTwo)
        assertEquals(true, 1024.isPowerOfTwo)
        assertEquals(false, 1025.isPowerOfTwo)
        for (n in 0..31) {
            if (n >= 2) assertEquals(false, ((1 shl n) - 1).isPowerOfTwo)
            assertEquals(true, (1 shl n).isPowerOfTwo)
            if (n >= 2) assertEquals(false, ((1 shl n) + 1).isPowerOfTwo)
        }
    }

    @Test
    fun nextPowerOfTwo() {
        assertEquals(0, 0.nextPowerOfTwo)
        assertEquals(1, 1.nextPowerOfTwo)
        assertEquals(2, 2.nextPowerOfTwo)
        assertEquals(4, 3.nextPowerOfTwo)
        assertEquals(4, 4.nextPowerOfTwo)
        assertEquals(8, 5.nextPowerOfTwo)
        assertEquals(16, 10.nextPowerOfTwo)
        assertEquals(16, 16.nextPowerOfTwo)
        assertEquals(32, 17.nextPowerOfTwo)
        assertEquals(64, 33.nextPowerOfTwo)
        assertEquals(64, 64.nextPowerOfTwo)
        assertEquals(128, 65.nextPowerOfTwo)
        assertEquals(128, 127.nextPowerOfTwo)
        assertEquals(128, 128.nextPowerOfTwo)
    }


    @Test
    fun prevPowerOfTwo() {
        assertEquals(0, 0.prevPowerOfTwo)
        assertEquals(1, 1.prevPowerOfTwo)
        assertEquals(2, 2.prevPowerOfTwo)
        assertEquals(2, 3.prevPowerOfTwo)
        assertEquals(4, 4.prevPowerOfTwo)
        assertEquals(4, 5.prevPowerOfTwo)
        assertEquals(8, 10.prevPowerOfTwo)
        assertEquals(16, 16.prevPowerOfTwo)
        assertEquals(16, 17.prevPowerOfTwo)
        assertEquals(32, 33.prevPowerOfTwo)
        assertEquals(64, 64.prevPowerOfTwo)
        assertEquals(64, 65.prevPowerOfTwo)
        assertEquals(64, 127.prevPowerOfTwo)
        assertEquals(128, 128.prevPowerOfTwo)
    }
}