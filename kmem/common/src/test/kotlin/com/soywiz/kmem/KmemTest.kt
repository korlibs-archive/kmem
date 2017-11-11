package com.soywiz.kmem

import org.junit.Test
import kotlin.test.assertEquals

class KmemTest {
	@Test
	fun testBasicUsage() {
		val data = MemBufferAlloc(16)

		val i8 = data.asInt8Buffer()
		i8[0] = 0
		i8[1] = 1
		i8[2] = 2
		i8[3] = 3

		i8[4] = 4
		i8[5] = 5
		i8[6] = 6
		i8[7] = 7

		val i32 = data.asInt32Buffer()
		assertEquals(0x03020100, i32[0])
		assertEquals(0x07060504, i32[1])

		val i32_off1 = i32.subarray(1)
		assertEquals(0x07060504, i32_off1[0])
		i32_off1[1] = 0x0B0A0908

		assertEquals(0x0B0A0908, i32[2])
	}

	@Test
	fun testArrayCopyOverlapping() {
		val i32 = Int32BufferAlloc(10)
		i32[0] = 0x01020304
		i32[1] = 0x05060708
		arraycopy(i32, 0, i32, 1, 4)
		assertEquals(0x01020304, i32[0])
		assertEquals(0x01020304, i32[1])
		assertEquals(0x05060708, i32[2])
		assertEquals(0x00000000, i32[3])
		assertEquals(0x00000000, i32[4])
	}
}